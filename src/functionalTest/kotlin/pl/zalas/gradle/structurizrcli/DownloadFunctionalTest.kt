/*
 * Copyright 2020 Jakub Zalas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.zalas.gradle.structurizrcli

import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DownloadFunctionalTest : FunctionalTest {

    @Test
    fun `it downloads structurizr cli`(@TempDir projectDir: File) {
        givenConfiguration(projectDir, """
            plugins {
                id 'pl.zalas.structurizr-cli'
            }
            structurizrCli {
                version = "1.30.0"
            }
        """)

        execute(projectDir, "structurizrCliDownload")

        assertTrue(File("${projectDir.absolutePath}/build/downloads/structurizr-cli-1.30.0.zip").exists())
    }

    @Test
    fun `it downloads structurizr cli to a custom downloads directory`(@TempDir projectDir: File) {
        givenConfiguration(projectDir, """
            plugins {
                id 'pl.zalas.structurizr-cli'
            }
            structurizrCli {
                version = "1.20.1"
                download {
                    directory = "tmp"
                }
            }
        """)

        execute(projectDir, "structurizrCliDownload")

        assertTrue(File("${projectDir.absolutePath}/tmp/structurizr-cli-1.20.1.zip").exists())
    }

    @Test
    fun `it downloads the latest version of structurizr cli`(@TempDir projectDir: File) {
        givenConfiguration(projectDir, """
            plugins {
                id 'pl.zalas.structurizr-cli'
            }
            structurizrCli {
            }
        """)

        execute(projectDir, "structurizrCliDownload")

        val downloadedFiles = File("${projectDir.absolutePath}/build/downloads/")
            .listFiles { _, fileName -> fileName.matches("structurizr-cli-.*\\.zip".toRegex()) }

        assertEquals(1, downloadedFiles.size)
    }
}
