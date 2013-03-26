/*
 * Copyright (C) 2013 USAC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.usac.eco.classroom.da.impl;

import com.zodiac.db.AbstractDAO;
import java.sql.SQLException;
import org.usac.eco.classroom.da.DAOCourse;
import org.usac.eco.libdto.DTOCourse;
import org.usac.eco.libdto.DTOCourseSchedule;
import org.usac.eco.libdto.DTOCycle;
import org.usac.eco.libdto.DTOPeriod;
import org.usac.eco.libdto.DTOSection;
import org.usac.eco.libdto.DTOUser;

/**
 *
 * @author Brian Estrada <brianseg014@gmail.com>
 */
public class DAOCoursePostgreSQL extends AbstractDAO<DTOCourse> implements DAOCourse {
 
    @Override
    public DTOCourse getDTO() throws SQLException {
        return new DTOCourse(
                getResultSet().getInt("course_id"),
                new DTOCycle(
                    getResultSet().getInt("cycle_id"), 
                    getResultSet().getNString("cycle_name"), 
                    new DTOPeriod(
                        getResultSet().getInt("period_id"), 
                        getResultSet().getString("period_name"), 
                        getResultSet().getTime("start_date"), 
                        getResultSet().getTime("end_date")), 
                    getResultSet().getInt("year")),
                new DTOSection(
                    getResultSet().getInt("section_id"), 
                    getResultSet().getString("section_name")),
                getResultSet().getString("course_name"), 
                getResultSet().getInt("subscribers"), 
                getResultSet().getInt("connected"), 
                getResultSet().getString("uri"), 
                new DTOUser(
                    getResultSet().getInt("user_id"), 
                    getResultSet().getString("user_name"), 
                    null, 
                    null, 
                    null, 
                    DTOUser.Profile.PROFESSOR, null),
                DTOCourse.Status.valueOf(getResultSet().getString("status_name")),
                null);
    }

    @Override
    public void getCourses(DTOUser dtoUser) throws SQLException {
        String sql = "SELECT "
                + "  c.course_id, c.course_name, co.subscribers,"
                + "  co.connected, co.uri, u.user_id, u.user_name, "
                + "  cs.section_id, cs.section_name, cy.cycle_name,"
                + "  cy.year, cst.status_id, cst.status_name, p.period_id,"
                + "  p.period_name, p.start_date, p.end_date "
                + "FROM "
                + "  course_open co, course c, course_section cs, "
                + "  cycle cy, course_status cst, period p, \"user\" u "
                + "WHERE "
                + "  co.course_id = c.course_name "
                + "  AND co.profesor_id = u.user_id "
                + "  AND cs.section_id = co.section_id "
                + "  AND cy.cycle_id = co.cycle_id "
                + "  AND cst.status_id = co.status_id "
                + "  AND p.period_id = cy.period_id "
                + "  AND u.user_id = ? ";
        setQuery();
        getQuery().setQueryString(sql);
        getQuery().addQueryParams(dtoUser.getUserId());
        getQuery().execute();
        setResultSet(getQuery().getResultSet());
    }

    @Override
    public void searchCourse(DTOCourse dtoCourse) throws SQLException {
        String sql = "SELECT "
                + "  c.course_id, c.course_name, co.subscribers,"
                + "  co.connected, co.uri, u.user_id, u.user_name, "
                + "  cs.section_id, cs.section_name, cy.cycle_name,"
                + "  cy.year, cst.status_id, cst.status_name, p.period_id,"
                + "  p.period_name, p.start_date, p.end_date "
                + "FROM "
                + "  course_open co, course c, course_section cs, "
                + "  cycle cy, course_status cst, period p, \"user\" u "
                + "WHERE "
                + "  co.course_id = c.course_name "
                + "  AND co.profesor_id = u.user_id "
                + "  AND cs.section_id = co.section_id "
                + "  AND cy.cycle_id = co.cycle_id "
                + "  AND cst.status_id = co.status_id "
                + "  AND p.period_id = cy.period_id "
                + "  AND c.course_name LIKE '%?%' ";
        setQuery();
        getQuery().setQueryString(sql);
        getQuery().addQueryParams(dtoCourse.getCourseName());
        getQuery().execute();
        setResultSet(getQuery().getResultSet());
    }

    @Override
    public void subscribe(DTOUser dtoUser, DTOCourse dtoCourse) throws SQLException {
        String sql = "INSERT INTO "
                + "  course_subscriber "
                + "VALUES (?,?,?,?)";
        setQuery();
        getQuery().setQueryString(sql);
        getQuery().addQueryParams(dtoCourse.getCourseId(),
                                  dtoCourse.getCycle().getCycleId(),
                                  dtoCourse.getSection().getSectionId(),
                                  dtoUser.getUserId());
        getQuery().execute();
    }
    
    @Override
    public void changeState(DTOCourse dtoCourse) throws SQLException {
        String sql = "UPDATE "
                + "  course_open "
                + "SET "
                + "  status_id = ? "
                + "WHERE "
                + "  course_id = ? "
                + "  AND cycle_id = ? "
                + "  AND section_id = ? ";
        setQuery();
        getQuery().setQueryString(sql);
        getQuery().addQueryParams(dtoCourse.getStatus().getStatusId(),
                                  dtoCourse.getCourseId(),
                                  dtoCourse.getCycle().getCycleId(),
                                  dtoCourse.getSection().getSectionId());
        getQuery().execute();
    }

    @Override
    public void connectCourse(DTOCourse dtoCourse) throws SQLException {
        String sql = "UPDATE "
                + "  course_open "
                + "SET "
                + "  connected = connected + 1 "
                + "WHERE "
                + "  course_id = ? "
                + "  AND cycle_id = ? "
                + "  AND section_id = ? ";
        setQuery();
        getQuery().setQueryString(sql);
        getQuery().addQueryParams(dtoCourse.getCourseId(),
                                  dtoCourse.getCycle().getCycleId(),
                                  dtoCourse.getSection().getSectionId());
        getQuery().execute();
    }

    @Override
    public void unpublish(DTOCourse dtoCourse) throws SQLException {
        String sql = "UPDATE "
                + "  course_open "
                + "SET "
                + "  status_id = " + DTOCourse.Status.CONNECTED.getStatusId() + ", "
                + "  connected = 0 "
                + "WHERE "
                + "  course_id = ? "
                + "  AND cycle_id = ? "
                + "  AND section_id = ? ";
        setQuery();
        getQuery().setQueryString(sql);
        getQuery().addQueryParams(dtoCourse.getStatus().getStatusId(),
                                  dtoCourse.getCourseId(),
                                  dtoCourse.getCycle().getCycleId(),
                                  dtoCourse.getSection().getSectionId());
    }

    @Override
    public void publish(DTOCourse dtoCourse) throws SQLException {
        String sql = "UPDATE "
                + "  course_open "
                + "SET "
                + "  status_id = " + DTOCourse.Status.DISCONNECTED.getStatusId() + ", "
                + "  connected = 0 "
                + "WHERE "
                + "  course_id = ? "
                + "  AND cycle_id = ? "
                + "  AND section_id = ? ";
        setQuery();
        getQuery().setQueryString(sql);
        getQuery().addQueryParams(dtoCourse.getStatus().getStatusId(),
                                  dtoCourse.getCourseId(),
                                  dtoCourse.getCycle().getCycleId(),
                                  dtoCourse.getSection().getSectionId());
    }
    
}
