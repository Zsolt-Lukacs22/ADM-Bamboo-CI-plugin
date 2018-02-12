package com.adm.utils.uft.sdk.handler;

import com.adm.utils.uft.sdk.request.EventLogRequest;
import com.adm.utils.uft.StringUtils;
import com.adm.utils.uft.XPathUtils;
import com.adm.utils.uft.sdk.Client;
import com.adm.utils.uft.sdk.Logger;
import com.adm.utils.uft.sdk.Response;

import java.util.List;
import java.util.Map;

public class EventLogHandler extends Handler {

    private String _timeslotId = StringUtils.EMPTY_STRING;
    private int _lastRead = -1;

    public EventLogHandler(Client client, String timeslotId) {

        super(client, timeslotId);
        _timeslotId = timeslotId;
    }

    public boolean log(Logger logger) {

        boolean ret = false;
        Response eventLog = null;
        try {
            eventLog = getEventLog();
            String xml = eventLog.toString();
            List<Map<String, String>> entities = XPathUtils.toEntities(xml);
            for (Map<String, String> currEntity : entities) {
                if (isNew(currEntity)) {
                    logger.log(String.format(
                            "%s:%s",
                            currEntity.get("creation-time"),
                            currEntity.get("description")));
                }
            }
            ret = true;
        } catch (Throwable cause) {
            logger.log(String.format(
                    "Failed to print Event Log: %s (run id: %s, reservation id: %s). Cause: %s",
                    eventLog,
                    _runId,
                    _timeslotId,
                    cause));
        }

        return ret;
    }

    private boolean isNew(Map<String, String> currEntity) {

        boolean ret = false;
        int currEvent = Integer.parseInt(currEntity.get("id"));
        if (currEvent > _lastRead) {
            _lastRead = currEvent;
            ret = true;
        }

        return ret;
    }

    private Response getEventLog() {

        return new EventLogRequest(_client, _timeslotId).execute();
    }

}

