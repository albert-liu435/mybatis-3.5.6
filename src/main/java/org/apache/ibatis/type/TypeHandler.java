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
package org.apache.ibatis.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * mybatis中的所有类型转换器都继承了TypeHandler接口
 * 其中 setParameter 方法用于把 java 对象设置到 PreparedStatement 的参数中，getResult 方法用于从 ResultSet（根据列名或者索引位置获取）
 * 或 CallableStatement（根据存储过程获取） 中取出数据转换为 java 对象。
 *
 * @author Clinton Begin
 */
public interface TypeHandler<T> {

  /**
   * 负责将数据由JdbcType类型转换成java类型
   * 在通过PreparedStatement为SQL语句绑定参数时，会将数据由JdbcType类型转换成Java类型
   *
   * @param ps
   * @param i
   * @param parameter
   * @param jdbcType
   * @throws SQLException
   */
  void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException;

  /**
   * 方法及其重载方法负责将数据由java类型转换成JdbcType类型
   * 在通过getResult获取数据时，会将数据由Java类型转换成JdbcType类型
   * Gets the result.
   *
   * @param rs         the rs
   * @param columnName Colunm name, when configuration <code>useColumnLabel</code> is <code>false</code>
   * @return the result
   * @throws SQLException the SQL exception
   */
  T getResult(ResultSet rs, String columnName) throws SQLException;

  T getResult(ResultSet rs, int columnIndex) throws SQLException;

  T getResult(CallableStatement cs, int columnIndex) throws SQLException;

}
