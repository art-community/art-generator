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
import java.nio.file.StandardOpenOption.READ
import kotlin.io.path.createFile
import kotlin.io.path.exists

object Generator {
    @JvmStatic
    fun main(arguments: Array<String>) {
        if (configuration.lock.exists()) {
            return
        }
        configuration.lock.createFile()
        open(configuration.lock, READ).use { channel ->
            channel.lock()
            activator(arguments)
                    .mainModuleId(Generator::class.simpleName)
                    .module(scheduler().with(logging()))
                    .onUnload { if (configuration.lock.exists()) configuration.lock.toFile().delete() }
                    .launch()
            initialize()
            scheduleDelayed(configuration.watcherPeriod, ::watchSources)
            block()
        }
    }
}
