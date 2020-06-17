FROM redis:latest

LABEL author="Carlos Jafet Neto"

RUN ["apt-get", "update"]
RUN ["apt-get", "install", "-y", "vim"]

COPY ./redis.conf /usr/local/etc/redis/redis.conf

VOLUME ["/data", "/data"]

EXPOSE 6379

CMD [ "redis-server", "/usr/local/etc/redis/redis.conf" ]
