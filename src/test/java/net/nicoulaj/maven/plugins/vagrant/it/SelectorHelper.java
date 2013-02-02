/*
 * Copyright 2013 vagrant-maven-plugin contributors.
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
package net.nicoulaj.maven.plugins.vagrant.it;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Utilities used by maven-invoker-plugin selector scripts.
 * <p/>
 * <p>See {@code src/main/it/projects/.../selector.bsh} scripts.</p>
 *
 * @author <a href="http://github.com/nicoulaj">Julien Nicoulaud</a>
 * @since 1.0
 */
public class SelectorHelper {

    /** @return {@code true} if VirtualBox is installed. */
    public static boolean isVirtualBoxAvailable() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("VBoxManage --help").getInputStream()));
            return reader.readLine().contains("VirtualBox Command Line Management Interface");
        } catch (Throwable t) {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException mute) {
                }
            return false;
        }
    }
}
