/*
 * ART
 *
 * Copyright 2019-2021 ART
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress(JAVA_MODULE_SUPPRESSION)

package io.art.generator

import io.art.core.extensions.ThreadExtensions.block
import io.art.generator.configuration.configuration
import io.art.generator.constants.JAVA_MODULE_SUPPRESSION
import io.art.generator.service.SourceWatchingService.watchSources
import io.art.generator.service.initialize
import io.art.launcher.Activator.activator
import io.art.logging.module.LoggingActivator.logging
import io.art.scheduler.Scheduling.scheduleDelayed
import io.art.scheduler.module.SchedulerActivator.scheduler
import java.nio.channels.FileChannel.open
import java.nio.channels.FileLock
import java.nio.file.StandardOpenOption.READ
import java.nio.file.StandardOpenOption.WRITE
import kotlin.io.path.createFile
import kotlin.io.path.exists

object Generator {
    lateinit var lock: FileLock

    @JvmStatic
    fun main(arguments: Array<String>) {
        activator(arguments)
                .mainModuleId(Generator::class.simpleName)
                .module(scheduler().with(logging()))
                .onUnload {
                    if (configuration.lock.exists() && ::lock.isInitialized) {
                        lock.release()
                        configuration.lock.toFile().delete()
                    }
                }
                .launch()
        initialize()
        if (configuration.lock.exists()) return
        configuration.lock.createFile()
        open(configuration.lock, READ, WRITE).use { channel ->
            lock = channel.lock()
            if (!lock.isValid) {
                return
            }
            scheduleDelayed(configuration.watcherPeriod, ::watchSources)
            block()
        }
    }
}
