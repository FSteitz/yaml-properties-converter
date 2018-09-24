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

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import lombok.NonNull;
import org.snakeyaml.engine.v1.api.Load;
import org.snakeyaml.engine.v1.api.LoadSettings;
import org.snakeyaml.engine.v1.api.LoadSettingsBuilder;

/**
 * @author Florian Steitz (fst)
 */
public class PropertiesReader
{
   private LoadSettings settings = new LoadSettingsBuilder().build();
   private Load load = new Load(settings);

   /**
    * @param inputStream
    * @return
    */
   public Properties readFlattenedYaml(@NonNull InputStream inputStream)
   {
      return PropertiesFlattener.flatten(readYaml(inputStream));
   }

   /**
    * @param inputStream
    */
   public Properties readYaml(@NonNull InputStream inputStream)
   {
      Object object = load.loadFromInputStream(inputStream);

      if( !(object instanceof Map) )
      {
         throw new IllegalArgumentException("Read properties file must be representable as a map");
      }

      return toProperties((Map) object);
   }

   /**
    * @param inputStream
    */
   public Properties readProperties(@NonNull InputStream inputStream) throws IOException
   {
      Properties properties = new Properties();
      properties.load(inputStream);

      return properties;
   }

   /**
    * @param propertiesMap
    * @return
    */
   private Properties toProperties(Map propertiesMap)
   {
      Properties properties = new Properties();
      properties.putAll(propertiesMap);

      return properties;
   }
}
