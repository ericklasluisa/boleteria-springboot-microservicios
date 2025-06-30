from locust import HttpUser, task, between
import random

class MyUser(HttpUser):
  host = "http://localhost:8080"

  wait_time = between(0.5, 1.5)

  # @task
  # def crear_autor(self):
  #   payload = {
  #     "nombre": f"Autor {random.randint(1, 100)}",
  #     "apellido": f"Apellido {random.randint(1, 100)}",
  #     "email": f"{random.randint(1, 100)}@gmail.com",
  #     "orcid": f"{random.randint(1000000, 9999999)}",
  #     "nacionalidad": "Ecuador",
  #     "telefono": f"{random.randint(100000000, 999999999)}",
  #     "institucion": "ESPE"
  #   }
    
  #   self.client.post("/api/autor", json=payload)

  @task
  def crear_libro(self):
    payload = {
      "titulo": f"Libro {random.randint(1, 100)}",
      "anioPublicacion": random.randint(2000, 2023),
      "editorial": f"Editorial {random.randint(1, 100)}",
      "isbn": f"{random.randint(100000000, 999999999)}",
      "resumen": f"Este es un resumen del libro {random.randint(1, 100)}.",
      "idioma": "Español",
      "genero": "Ficción",
      "numeroPaginas": random.randint(100, 500),
      "edicion": "Primera",
      "autor": {
        "id": random.randint(5, 100)
      }
    }
    self.client.post("/api/libros", json=payload)
    
  @task
  def crear_articulo(self):
    payload = {
      "titulo": f"Libro {random.randint(1, 100)}",
      "anioPublicacion": random.randint(2000, 2023),
      "editorial": f"Editorial {random.randint(1, 100)}",
      "isbn": f"{random.randint(100000000, 999999999)}",
      "resumen": f"Este es un resumen del libro {random.randint(1, 100)}.",
      "idioma": "Español",
      "doi": f"{random.randint(1000000, 9999999)}",
      "revista": "revista",
      "volumen": "1",
      "numero": "1",
      "paginas": "200",
      "mesPublicacion": "Septiembre",
      "tipoArticulo": "Cientifico",
      "autor": {
        "id": random.randint(5, 100)
      }
    }
    self.client.post("/api/articulos", json=payload)