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

import io.art.core.context.Context.active
import io.art.core.context.Context.scheduleTermination
import io.art.core.extensions.ThreadExtensions.block
import io.art.core.waiter.Waiter.waitCondition
import io.art.generator.configuration.configuration
import io.art.generator.configuration.reconfigure
import io.art.generator.constants.JAVA_MODULE_SUPPRESSION
import io.art.generator.constants.LOCK_VALIDATION_PERIOD
import io.art.generator.constants.STOPPING_TIMEOUT
import io.art.generator.service.ControllerService.isStopping
import io.art.generator.service.ControllerService.lockIsValid
import io.art.generator.service.ControllerService.markAvailable
import io.art.generator.service.ControllerService.updateLock
import io.art.generator.service.SourceWatchingService.watchSources
import io.art.generator.service.initialize
import io.art.launcher.Activator.activator
import io.art.logging.module.LoggingActivator.logging
import io.art.scheduler.Scheduling.scheduleDelayed
import io.art.scheduler.module.SchedulerActivator.scheduler

object Generator {
    @JvmStatic
    fun main(arguments: Array<String>) {
        activator(arguments)
                .mainModuleId(Generator::class.simpleName)
                .module(scheduler().with(logging()))
                .onUnload(::markAvailable)
                .launch()
        initialize()
        reconfigure()

        if (isStopping()) {
            if (!waitCondition(STOPPING_TIMEOUT) { lockIsValid() }) {
                return
            }
        }

        if (!lockIsValid()) return

        scheduleDelayed(LOCK_VALIDATION_PERIOD) {
            if (!isStopping() && active()) {
                updateLock()
            }
        }

        scheduleDelayed(configuration.watcherPeriod) {
            if (!active()) {
                return@scheduleDelayed
            }
            if (isStopping()) {
                scheduleTermination()
                return@scheduleDelayed
            }
            reconfigure()
            watchSources()
        }

        block()
    }
}
