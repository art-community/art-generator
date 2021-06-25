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

import io.art.core.context.Context.scheduleTermination
import io.art.generator.constants.JAVA_MODULE_SUPPRESSION
import io.art.launcher.Activator.activator
import io.art.logging.module.LoggingActivator.logging
import io.art.scheduler.Scheduling.schedule
import io.art.scheduler.module.SchedulerActivator.scheduler
import java.lang.System.setProperty

object Generator {
    @JvmStatic
    fun main(arguments: Array<String>) {
        setProperty("configuration", "G:\\Development\\Projects\\art\\art-environment\\local\\projects\\sandbox\\build\\generator\\module.yml")
        activator(arguments)
                .mainModuleId(Generator::class.simpleName)
                .module(scheduler().with(logging()))
                .launch()

        schedule {
            while (!Thread.interrupted()) {
            }
        }

        scheduleTermination()
    }
}
