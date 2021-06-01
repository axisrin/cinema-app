#!/usr/bin/env sh

heroku apps:create low-film
heroku addons:create heroku-postgresql:hobby-dev --app low-film