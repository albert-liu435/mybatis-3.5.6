/**
 * Copyright 2009-2019 the original author or authors.
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
package org.apache.ibatis.executor.resultset;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.ibatis.cursor.Cursor;

/**
 * 在Statementhandler接口执行完指定的select语句之后，会将查询的结果集交给ResultSetHandler完成映射处理，处理负责select语句查询的结果集，还会处理存储过程执行后的输出参数
 *
 * @author Clinton Begin
 */
public interface ResultSetHandler {

  /**
   * 处理结果集，生成相应的结果对象集合
   *
   * @param stmt
   * @param <E>
   * @return
   * @throws SQLException
   */
  <E> List<E> handleResultSets(Statement stmt) throws SQLException;

  /**
   * 处理结果集，返回相应的游标对象
   *
   * @param stmt
   * @param <E>
   * @return
   * @throws SQLException
   */
  <E> Cursor<E> handleCursorResultSets(Statement stmt) throws SQLException;

  /**
   * 处理存储过程的输出参数
   *
   * @param cs
   * @throws SQLException
   */
  void handleOutputParameters(CallableStatement cs) throws SQLException;

}