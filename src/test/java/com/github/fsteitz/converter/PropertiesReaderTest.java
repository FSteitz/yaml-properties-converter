/*
 * Copyright 2018 SHD Einzelhandelssoftware GmbH
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
package com.github.fsteitz.converter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Florian Steitz (fst)
 */
public class PropertiesReaderTest
{
   private static final String BASE_PATH = "src/test/resources/";
   private static final Path INPUT_PROPERTIES_PATH = Paths.get(BASE_PATH + "input.properties");
   private static final Path INPUT_YAML_PATH = Paths.get(BASE_PATH + "input.yaml");

   private PropertiesReader propertiesReader = new PropertiesReader();

   @Test
   public void testFromYaml() throws Exception
   {
      Properties properties = propertiesReader.readFlattenedYaml(Files.newInputStream(INPUT_YAML_PATH));
      assertThat(properties, is(propertiesReader.readProperties(Files.newInputStream(INPUT_PROPERTIES_PATH))));
   }

   @Test
   public void testFromProperties() throws Exception
   {
      propertiesReader.readProperties(Files.newInputStream(INPUT_PROPERTIES_PATH));
   }
}
