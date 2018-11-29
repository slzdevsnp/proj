#!/usr/bin/env python3
import os

print('Credendtials from environment: {}'.format(
    os.environ.get('GOOGLE_APPLICATION_CREDENTIALS')))
