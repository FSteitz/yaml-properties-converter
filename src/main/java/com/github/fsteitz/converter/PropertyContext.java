package com.github.fsteitz.converter;

import java.util.Map;

import com.github.fsteitz.converter.writer.PropertyFileWriter;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Florian Steitz (fst)
 */
@Data
@AllArgsConstructor
public class PropertyContext
{
   private Map<Object, Object> properties;
   private PropertyFileWriter writer;
}
