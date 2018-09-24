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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author Florian Steitz (fst)
 */
public final class PropertiesFlattener
{
   private static final String DOT = ".";
   private static final String COMMA = ",";

   private PropertiesFlattener()
   {

   }

   /**
    * @param properties
    * @return
    */
   public static Properties flatten(Map<Object, Object> properties)
   {
      Collection<KeyValuePair> keyValuePairs = new ArrayList<>();
      flatten(keyValuePairs, new KeyValuePairBuilder(), properties);

      return keyValuePairs.stream().collect(
            Properties::new,
            (props, pair) -> props.put(pair.key, pair.value),
            Properties::putAll
      );
   }

   /**
    * @param pairs
    * @param builder
    * @param nestedProperties
    */
   @SuppressWarnings("unchecked")
   private static void flatten(Collection<KeyValuePair> pairs, KeyValuePairBuilder builder, Map<Object, Object> nestedProperties)
   {
      for( Map.Entry<Object, Object> propertyEntry : nestedProperties.entrySet() )
      {
         KeyValuePairBuilder builderCopy = new KeyValuePairBuilder(builder);
         String key = (String) propertyEntry.getKey();
         Object value = propertyEntry.getValue();

         builderCopy.appendKeyPart(key);

         if( value instanceof Map )
         {
            flatten(pairs, builderCopy, (Map<Object, Object>) value);
         }
         else if( value instanceof Collection )
         {
            pairs.add(builderCopy.build(joinValues((Collection<Object>) value)));
         }
         else
         {
            pairs.add(builderCopy.build(value));
         }
      }
   }

   /**
    * @param values
    * @return
    */
   private static String joinValues(Collection<Object> values)
   {
      return values.stream().map(Object::toString).collect(Collectors.joining(COMMA));
   }

   /**
    *
    */
   @NoArgsConstructor
   private static final class KeyValuePairBuilder
   {
      private StringBuilder keyBuilder = new StringBuilder();

      /**
       * @param keyValuePairBuilder
       */
      private KeyValuePairBuilder(@NonNull KeyValuePairBuilder keyValuePairBuilder)
      {
         keyBuilder.append(keyValuePairBuilder.keyBuilder);
      }

      /**
       * @param key
       */
      private void appendKeyPart(String key)
      {
         if( keyBuilder.length() > 0 )
         {
            keyBuilder.append(DOT);
         }

         keyBuilder.append(key);
      }

      /**
       * @param value
       * @return
       */
      private KeyValuePair build(Object value)
      {
         return new KeyValuePair(keyBuilder.toString(), value);
      }
   }

   /**
    *
    */
   @AllArgsConstructor
   private static final class KeyValuePair
   {
      private String key;
      private Object value;
   }
}
