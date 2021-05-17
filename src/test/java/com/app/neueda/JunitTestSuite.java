package com.app.neueda;

import com.app.neueda.logic.Base62URLGeneratorTest;
import com.app.neueda.services.ShortURLServiceTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectPackages("com.app.neueda")
public class JunitTestSuite {
}
