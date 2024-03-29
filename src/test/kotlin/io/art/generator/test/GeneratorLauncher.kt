/*
 * ART
 *
 * Copyright 2019-2022 ART
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

package io.art.generator.test

import io.art.configurator.module.ConfiguratorActivator.configurator
import io.art.generator.Generator
import io.art.generator.configuration.reconfigure
import io.art.generator.service.common.SourceScanningService.scanSources
import io.art.generator.service.common.initialize
import io.art.launcher.Activator.activator
import io.art.logging.module.LoggingActivator.logging

fun main() {
    activator()
            .main(Generator::class.simpleName)
            .module(configurator())
            .module(logging())
            .launch()
    initialize()
    reconfigure()
    scanSources()
}
