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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snakeyaml.engine.v1.api.Dump;
import org.snakeyaml.engine.v1.api.DumpSettings;
import org.snakeyaml.engine.v1.api.DumpSettingsBuilder;

/**
 * @author Florian Steitz (fst)
 */
public class YamlWriter implements PropertyFileWriter
{
   private static final Logger LOGGER = LoggerFactory.getLogger(YamlWriter.class);

   private DumpSettings settings = new DumpSettingsBuilder().build();
   private Dump dump = new Dump(settings);

   private Map yaml;

   /**
    * @param yaml
    */
   public YamlWriter(@NonNull Map yaml)
   {
      this.yaml = yaml;
   }

   /**
    * @param fileName
    */
   @Override
   public void write(@NonNull String fileName)
   {
      try
      {
         Files.write(Paths.get(fileName), dump.dumpToString(yaml).getBytes(StandardCharsets.UTF_8));
      }
      catch(IOException e)
      {
         LOGGER.error(String.format("File '%s' could not be written", fileName), e);
      }
   }
}
