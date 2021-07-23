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

import io.art.configurator.module.ConfiguratorActivator.configurator
import io.art.core.extensions.ThreadExtensions.block
import io.art.generator.Generator
import io.art.generator.configuration.configuration
import io.art.generator.configuration.reconfigure
import io.art.generator.service.SourceWatchingService.watchSources
import io.art.generator.service.initialize
import io.art.launcher.Activator.activator
import io.art.logging.module.LoggingActivator.logging
import io.art.scheduler.Scheduling.scheduleFixedRate
import io.art.scheduler.module.SchedulerActivator.scheduler

fun main() {
    activator()
            .main(Generator::class.simpleName)
            .module(configurator())
            .module(logging())
            .module(scheduler())
            .launch()
    initialize()
    reconfigure()
    scheduleFixedRate(configuration.watcherPeriod, ::watchSources)
    block()
}
