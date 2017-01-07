package com.udacity.gradle.builditbigger.testing;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.v4.util.Pair;
import android.util.Log;

import com.udacity.gradle.builditbigger.EndpointsAsyncTask;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

/**
 * Created by Ali on 1/4/2017.
 */

public class AsyncTaskTest {
    String joke=null;
    private Context instrumantationCtx;

    @Before
    public void setup() {
        instrumantationCtx = InstrumentationRegistry.getContext();
    }

    @Test
    public void verfiyNotEmpty(){
        EndpointsAsyncTask e=new EndpointsAsyncTask(new EndpointsAsyncTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                joke=output;
                Log.v("Test","Test processFinish:"+joke);
                assertNotNull(joke);
            }
        });
        e.execute(new Pair<Context, String>(instrumantationCtx, "Manfred"));

    }
}
