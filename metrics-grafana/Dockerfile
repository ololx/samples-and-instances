FROM grafana/grafana:8.0.4

#local storage folder
ENV LOCAL=/./local

COPY ./configuration/grafana.ini /etc/grafana/grafana.ini
COPY ./provisioning /etc/grafana/provisioning

EXPOSE 3000

VOLUME ${LOCAL}