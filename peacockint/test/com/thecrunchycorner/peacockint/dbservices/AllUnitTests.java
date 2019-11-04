package com.thecrunchycorner.peacockint.dbservices;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@SuiteClasses({ CustomerDaoImplTest.class, MsgLogDaoImplTest.class })
public class AllUnitTests
{

}
