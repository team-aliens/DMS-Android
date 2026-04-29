FROM node:22-slim

WORKDIR /app

COPY package*.json ./
RUN npm ci --omit=dev --ignore-scripts

COPY index.js ./

ENV PORT=8080

USER node

CMD ["node", "index.js"]
