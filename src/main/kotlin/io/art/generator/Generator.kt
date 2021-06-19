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

import io.art.core.constants.StringConstants.EMPTY_STRING
import io.art.core.extensions.DateTimeExtensions.toMillis
import io.art.core.extensions.ThreadExtensions.block
import io.art.generator.configuration.configuration
import io.art.generator.configuration.reconfigure
import io.art.generator.constants.JAVA_MODULE_SUPPRESSION
import io.art.generator.constants.LOCK_FILE_LAST_MODIFICATION_TIMESTAMP
import io.art.generator.constants.LOCK_FILE_MODIFICATION_PERIOD
import io.art.generator.service.SourceWatchingService.watchSources
import io.art.generator.service.initialize
import io.art.launcher.Activator.activator
import io.art.logging.module.LoggingActivator.logging
import io.art.scheduler.Scheduling.scheduleDelayed
import io.art.scheduler.module.SchedulerActivator.scheduler
import java.nio.channels.FileChannel
import java.nio.channels.FileChannel.open
import java.nio.channels.FileLock
import java.nio.file.Files.readAttributes
import java.nio.file.StandardOpenOption.READ
import java.nio.file.StandardOpenOption.WRITE
import java.nio.file.attribute.BasicFileAttributes
import java.time.LocalDateTime.now
import kotlin.io.path.createFile
import kotlin.io.path.exists
import kotlin.io.path.writeText

object Generator {
    lateinit var channel: FileChannel
    lateinit var lock: FileLock

    @JvmStatic
    fun main(arguments: Array<String>) {
        activator(arguments)
                .mainModuleId(Generator::class.simpleName)
                .module(scheduler().with(logging()))
                .onUnload {
                    if (configuration.lock.exists() && ::channel.isInitialized && ::lock.isInitialized) {
                        lock.release()
                        channel.close()
                        configuration.lock.toFile().delete()
                    }
                }
                .launch()
        initialize()
        reconfigure()
        if (configuration.lock.exists()) return

        val lastModifiedTime = readAttributes(configuration.lock, BasicFileAttributes::class.java).lastModifiedTime()
        if (lastModifiedTime.toMillis() > toMillis(now().minus(LOCK_FILE_LAST_MODIFICATION_TIMESTAMP))) {
            return
        }

        configuration.lock.createFile().apply { toFile().deleteOnExit() }
        channel = open(configuration.lock, READ, WRITE)
        lock = channel.lock()

        if (!lock.isValid) {
            return
        }

        scheduleDelayed(configuration.watcherPeriod) {
            reconfigure()
            watchSources()
        }

        scheduleDelayed(LOCK_FILE_MODIFICATION_PERIOD) {
            configuration.lock.writeText(EMPTY_STRING)
        }

        block()
    }
}
