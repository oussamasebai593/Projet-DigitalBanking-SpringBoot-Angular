# Étape 1 : build Angular
FROM node:18 AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build --prod

# Étape 2 : Nginx pour servir le front
FROM nginx:alpine
COPY --from=build /app/dist/ebanking-frontend /usr/share/nginx/html
EXPOSE 80
