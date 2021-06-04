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
import io.art.generator.service.common.initialize
import io.art.generator.service.java.JavaSourceWatchingService.watchJavaSources
import io.art.launcher.Activator.activator
import io.art.logging.module.LoggingActivator.logging
import io.art.scheduler.manager.Scheduling.scheduleDelayed
import io.art.scheduler.module.SchedulerActivator.scheduler

object Generator {
    @JvmStatic
    fun main(arguments: Array<String>) {
        activator(arguments)
                .mainModuleId(Generator::class.simpleName)
                .module(scheduler().with(logging()))
                .launch()
        initialize()
        scheduleDelayed(configuration.watcherPeriod, ::watchJavaSources)
        block()
    }
}