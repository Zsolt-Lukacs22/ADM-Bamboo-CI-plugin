package com.adm.utils.uft.sdk.request;

import com.adm.utils.uft.sdk.Client;

public class EventLogRequest extends GetRequest {

    private final String _timeslotId;

    public EventLogRequest(Client client, String timeslotId) {

        super(client, timeslotId);
        _timeslotId = timeslotId;
    }

    @Override
    protected String getSuffix() {

        return String.format(
                "event-log-reads?query={context[\"*Timeslot:%%20%s%%3B*\"]}&fields=id,event-type,creation-time,action,description",
                _timeslotId);
    }
}
