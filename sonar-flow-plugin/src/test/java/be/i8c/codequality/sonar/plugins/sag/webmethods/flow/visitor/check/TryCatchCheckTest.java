/*
 * i8c
 * Copyright (C) 2016 i8c NV
 * mailto:contact AT i8c DOT be
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package be.i8c.codequality.sonar.plugins.sag.webmethods.flow.visitor.check;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import be.i8c.codequality.sonar.plugins.sag.webmethods.flow.squid.FlowAstScanner;
import be.i8c.codequality.sonar.plugins.sag.webmethods.flow.visitor.SimpleMetricVisitor;
import be.i8c.codequality.sonar.plugins.sag.webmethods.flow.visitor.check.TryCatchCheck;
import be.i8c.codequality.sonar.plugins.sag.webmethods.flow.visitor.check.type.FlowCheck;

import com.sonar.sslr.api.Grammar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.squidbridge.SquidAstVisitor;
import org.sonar.squidbridge.api.CheckMessage;
import org.sonar.squidbridge.api.SourceFile;

public class TryCatchCheckTest {

  static final Logger logger = LoggerFactory.getLogger(TryCatchCheckTest.class);


  @Test
  public void tryCatchCheckValid() {
    List<SquidAstVisitor<Grammar>> metrics = new ArrayList<SquidAstVisitor<Grammar>>();
    metrics.add(new SimpleMetricVisitor());
    List<FlowCheck> checks = new ArrayList<FlowCheck>();
    checks.add(new TryCatchCheck());

    // check valid flow
    String validFlowPath 
        = "src/test/resources/WmPackage/ns/I8cFlowSonarPluginTest/pub/checkTryCatchValid/flow.xml";
    SourceFile sfCorrect = FlowAstScanner.scanSingleFile(new File(validFlowPath), checks, metrics);
    Set<CheckMessage> scmCorrect = sfCorrect.getCheckMessages();
    assertEquals(0, scmCorrect.size());

  }
  
  @Test
  public void tryCatchCheckInvalid() {
    List<SquidAstVisitor<Grammar>> metrics = new ArrayList<SquidAstVisitor<Grammar>>();
    metrics.add(new SimpleMetricVisitor());
    List<FlowCheck> checks = new ArrayList<FlowCheck>();
    checks.add(new TryCatchCheck());

    // check invalid flow
    String invalidFlowPath = "src/test/resources/WmPackage/ns/I8cFlowSonarPluginTest"
        + "/pub/checkTryCatchInvalid/flow.xml";
    String expectedMessage = "Create try-catch sequence";

    SourceFile sfViolation = FlowAstScanner.scanSingleFile(new File(invalidFlowPath), checks,
        metrics);
    List<CheckMessage> violationMessages = new ArrayList<CheckMessage>(
        sfViolation.getCheckMessages());
    assertEquals(1, violationMessages.size());
    assertTrue("Returned check message not as expected",
        expectedMessage.equals(violationMessages.get(0).getDefaultMessage()));

  }

}