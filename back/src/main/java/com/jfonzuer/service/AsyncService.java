package com.jfonzuer.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by pgm on 12/03/17.
 */
@Service
public class AsyncService {

    public void executeAsync(Runnable r) {

        ExecutorService service = null;
        try {
            service = Executors.newSingleThreadExecutor();
            service.submit(r);
        } finally {
            if (service != null) service.shutdown();
        }
    }
}
