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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

import com.github.fsteitz.converter.writer.PropertiesWriter;
import com.github.fsteitz.converter.writer.YamlWriter;
import io.codearte.props2yaml.Props2YAML;
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
    * @param fileName
    * @return
    */
   public PropertyContext readFlattenedYaml(@NonNull String fileName) throws IOException
   {
      PropertyContext propertyContext = readYaml(fileName);
      return new PropertyContext(PropertiesFlattener.flatten(propertyContext.getProperties()), propertyContext.getWriter());
   }

   /**
    * @param fileName
    */
   public PropertyContext readYaml(@NonNull String fileName) throws IOException
   {
      Object object = load.loadFromInputStream(Files.newInputStream(Paths.get(fileName)));

      if( !(object instanceof Map) )
      {
         throw new IllegalArgumentException("Read properties file must be representable as a map");
      }

      return createContext(toProperties((Map) object));
   }

   /**
    * @param fileName
    */
   public PropertyContext readProperties(@NonNull String fileName) throws IOException
   {
      Props2YAML props2YAML = Props2YAML.fromFile(Paths.get(fileName));
      Map<Object, Object> yaml = (Map<Object, Object>) load.loadFromString(props2YAML.convert());

      return new PropertyContext(yaml, new YamlWriter(yaml));
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

   /**
    * @param properties
    * @return
    */
   private PropertyContext createContext(Properties properties)
   {
      return new PropertyContext(properties, new PropertiesWriter(properties));
   }
}
