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
package com.github.fsteitz.converter.writer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Florian Steitz (fst)
 */
@AllArgsConstructor
public class PropertiesWriter implements PropertyFileWriter
{
   private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesWriter.class);

   private Properties properties;

   /**
    * @param fileName
    */
   @Override
   public void write(@NonNull String fileName)
   {
      try
      {
         properties.store(Files.newOutputStream(Paths.get(fileName)), "");
      }
      catch(IOException e)
      {
         LOGGER.error(String.format("File '%s' could not be written", fileName), e);
      }
   }
}
