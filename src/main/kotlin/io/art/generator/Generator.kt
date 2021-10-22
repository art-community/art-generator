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

import io.art.configurator.module.ConfiguratorActivator.configurator
import io.art.core.waiter.Waiter.waitCondition
import io.art.generator.configuration.reconfigure
import io.art.generator.constants.JAVA_MODULE_SUPPRESSION
import io.art.generator.constants.STOPPING_TIMEOUT
import io.art.generator.service.common.ControllerService.isStopping
import io.art.generator.service.common.ControllerService.lockIsValid
import io.art.generator.service.common.ControllerService.locked
import io.art.generator.service.common.ControllerService.markAvailable
import io.art.generator.service.common.ControllerService.updateLock
import io.art.generator.service.common.SourceScanningService.scanSources
import io.art.generator.service.common.initialize
import io.art.launcher.Activator.activator
import io.art.logging.Logging.logger
import io.art.logging.module.LoggingActivator.logging

object Generator {
    @JvmStatic
    fun main(arguments: Array<String>) {
        activator(arguments)
                .main(Generator::class.simpleName)
                .module(configurator())
                .module(logging())
                .onUnload(::markAvailable)
                .launch()
        initialize()
        reconfigure()

        if (isStopping()) {
            if (!waitCondition(STOPPING_TIMEOUT) { lockIsValid() }) {
                return
            }
        }

        if (!lockIsValid() || !locked()) return

        updateLock()
        try {
            scanSources()
        } catch (throwable: Throwable) {
            logger(Generator::class.java.name).error(throwable)
        }
    }
}
