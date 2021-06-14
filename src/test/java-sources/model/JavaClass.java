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

package model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public abstract class JavaClass {
    private boolean privateField;
    protected boolean protectedField;
    public boolean publicField;
    boolean internalField;

    //Must be ignored
    private boolean privateMethod() {
        return true;
    }

    //Must be ignored
    protected boolean protectedMethod() {
        return true;
    }

    //Must be ignored
    boolean internalMethod() {
        return true;
    }

    //Must be ignored
    protected abstract boolean protectedAbstractMethod();

    //Must be ignored
    abstract boolean internalAbstractMethod();

    public boolean publicMethod() {
        return true;
    }
    public abstract boolean publicAbstractMethod();
}
