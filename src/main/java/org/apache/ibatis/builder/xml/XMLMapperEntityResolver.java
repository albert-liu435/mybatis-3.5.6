/**
 * Copyright 2009-2020 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ibatis.builder.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.apache.ibatis.io.Resources;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 如果SAX 应用程序需要实现自定义处理外部实体，则必须实现此接口并使用setEntityResolver
 * 方法向SAX 驱动器注册一个实例。也就是说，对于解析一个XML, SAX 首先读取该XML 文
 * 档上的声明，根据声明去寻找相应的DTD 定义，以便对文档进行一个验证。默认的寻找规则，
 * 即通过网络（实现上就是声明的DTD 的URI 地址）来下载相应的DTD 声明，并进行认证。
 * 下载的过程是一个漫长的过程，而且当网络中断或不可用时，这里会报错，就是因为相应的DTD
 * 声明没有被找到的原因。
 * EntityResolv凹的作用是项目本身就可以提供一个如何寻找DTD 声明的方法，即由程序来
 * 实现寻找DTD 声明的过程，比如我们将DTD 文件放到项目中某处，在实现时直接将此文档读
 * 取并返回给SAX 即可。这样就避免了通过网络来寻找相应的声明。
 * Offline entity resolver for the MyBatis DTDs.
 *
 * @author Clinton Begin
 * @author Eduardo Macarron
 */
public class XMLMapperEntityResolver implements EntityResolver {

  private static final String IBATIS_CONFIG_SYSTEM = "ibatis-3-config.dtd";
  private static final String IBATIS_MAPPER_SYSTEM = "ibatis-3-mapper.dtd";
  private static final String MYBATIS_CONFIG_SYSTEM = "mybatis-3-config.dtd";
  private static final String MYBATIS_MAPPER_SYSTEM = "mybatis-3-mapper.dtd";

  private static final String MYBATIS_CONFIG_DTD = "org/apache/ibatis/builder/xml/mybatis-3-config.dtd";
  private static final String MYBATIS_MAPPER_DTD = "org/apache/ibatis/builder/xml/mybatis-3-mapper.dtd";

  /**
   * 将DTD文件转换为本地的DTD文件
   * Converts a public DTD into a local one.
   *
   * @param publicId The public id that is what comes after "PUBLIC"
   * @param systemId The system id that is what comes after the public id.
   * @return The InputSource for the DTD
   * @throws org.xml.sax.SAXException If anything goes wrong
   */
  @Override
  public InputSource resolveEntity(String publicId, String systemId) throws SAXException {
    try {
      if (systemId != null) {
        String lowerCaseSystemId = systemId.toLowerCase(Locale.ENGLISH);
        if (lowerCaseSystemId.contains(MYBATIS_CONFIG_SYSTEM) || lowerCaseSystemId.contains(IBATIS_CONFIG_SYSTEM)) {
          // mybatis全局配置文件`config.xml`对应的DTD文件
          return getInputSource(MYBATIS_CONFIG_DTD, publicId, systemId);
        } else if (lowerCaseSystemId.contains(MYBATIS_MAPPER_SYSTEM) || lowerCaseSystemId.contains(IBATIS_MAPPER_SYSTEM)) {
          // mybatis数据映射配置文件`*Mapper.xml`对应的DTD文件
          return getInputSource(MYBATIS_MAPPER_DTD, publicId, systemId);
        }
      }
      return null;
    } catch (Exception e) {
      throw new SAXException(e.toString());
    }
  }

  private InputSource getInputSource(String path, String publicId, String systemId) {
    InputSource source = null;
    if (path != null) {
      try {
        InputStream in = Resources.getResourceAsStream(path);
        source = new InputSource(in);
        source.setPublicId(publicId);
        source.setSystemId(systemId);
      } catch (IOException e) {
        // ignore, null is ok
      }
    }
    return source;
  }

}
