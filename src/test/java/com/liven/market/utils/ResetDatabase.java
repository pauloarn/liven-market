package com.liven.market.utils;

import org.flywaydb.core.Flyway;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class ResetDatabase extends AbstractTestExecutionListener {
    @Override
    public int getOrder() {
        return 2001;
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        var flyway = getFlyway(testContext);
        reset(flyway);
    }

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        var flyway = getFlyway(testContext);
        migrate(flyway);
    }

    private void reset(Flyway flyway) {
        flyway.clean();
    }

    private void migrate(Flyway flyway) {
        flyway.migrate();
    }

    private Flyway getFlyway(TestContext testContext) {
        return testContext.getApplicationContext().getBean(Flyway.class);
    }
}
