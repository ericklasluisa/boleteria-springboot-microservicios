# Pruebas de Rutas API - Mis Publicaciones

## Autor

### Listar autores
- **GET** `http://localhost:8080/api/autor`

### Crear autor
- **POST** `http://localhost:8080/api/autor`
- **Body (JSON):**
```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan.perez@ejemplo.com",
  "orcid": "0000-0001-2345-6789",
  "nacionalidad": "Ecuatoriana",
  "telefono": "0999999999",
  "institucion": "ESPE"
}
```

### Actualizar autor
- **PUT** `http://localhost:8080/api/autor/{id}`
- **Body (JSON):** igual que crear

---

## Libros

### Listar libros
- **GET** `http://localhost:8080/api/libros`

### Obtener libro por ID
- **GET** `http://localhost:8080/api/libros/{id}`

### Crear libro
- **POST** `http://localhost:8080/api/libros`
- **Body (JSON):**
```json
{
  "titulo": "Libro de Prueba",
  "anioPublicacion": 2024,
  "editorial": "Editorial X",
  "isbn": "1234567890",
  "resumen": "Resumen del libro",
  "idioma": "Español",
  "genero": "Ciencia",
  "numeroPaginas": "200",
  "edicion": "Primera",
  "autor": { "id": 1 }
}
```

### Actualizar libro
- **PUT** `http://localhost:8080/api/libros/{id}`
- **Body (JSON):** igual que crear

### Eliminar libro
- **DELETE** `http://localhost:8080/api/libros/{id}`

---

## Artículos

### Listar artículos
- **GET** `http://localhost:8080/api/articulos`

### Obtener artículo por ID
- **GET** `http://localhost:8080/api/articulos/{id}`

### Crear artículo
- **POST** `http://localhost:8080/api/articulos`
- **Body (JSON):**
```json
{
  "titulo": "Artículo de Prueba",
  "anioPublicacion": 2024,
  "editorial": "Editorial Y",
  "isbn": "9876543210",
  "resumen": "Resumen del artículo",
  "idioma": "Español",
  "doi": "10.1234/abcd.2024.01",
  "revista": "Revista Científica",
  "volumen": "10",
  "numero": "2",
  "paginas": "100-110",
  "mesPublicacion": "Junio",
  "tipoArticulo": "Investigación",
  "autor": { "id": 1 }
}
```

### Actualizar artículo
- **PUT** `http://localhost:8080/api/articulos/{id}`
- **Body (JSON):** igual que crear

### Eliminar artículo
- **DELETE** `http://localhost:8080/api/articulos/{id}`

---

## Publicaciones (Libros y Artículos)

### 
- **GET** `http://localhost:8080/api/publicaciones`

---
