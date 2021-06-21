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
import io.art.generator.configuration.reconfigure
import io.art.generator.constants.JAVA_MODULE_SUPPRESSION
import io.art.generator.constants.LOCK_VALIDATION_PERIOD
import io.art.generator.service.ControllerService.controllerFileExists
import io.art.generator.service.ControllerService.isStopping
import io.art.generator.service.ControllerService.lockIsValid
import io.art.generator.service.ControllerService.updateLock
import io.art.generator.service.SourceWatchingService.watchSources
import io.art.generator.service.initialize
import io.art.launcher.Activator.activator
import io.art.logging.module.LoggingActivator.logging
import io.art.scheduler.Scheduling.scheduleDelayed
import io.art.scheduler.module.SchedulerActivator.scheduler
import kotlin.system.exitProcess

object Generator {
    @JvmStatic
    fun main(arguments: Array<String>) {
        activator(arguments)
                .mainModuleId(Generator::class.simpleName)
                .module(scheduler().with(logging()))
                .launch()
        initialize()
        reconfigure()

        if (!lockIsValid()) return

        scheduleDelayed(LOCK_VALIDATION_PERIOD) {
            if (!isStopping()) {
                updateLock()
            }
        }

        scheduleDelayed(configuration.watcherPeriod) {
            if (isStopping()) {
                exitProcess(0)
            }
            reconfigure()
            watchSources()
        }

        block()
    }
}
