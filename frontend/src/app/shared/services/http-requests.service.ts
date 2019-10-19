import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HttpRequestsService {

  // TODO - setear url para pegarle al back
  url = 'http://localhost:9000';

  constructor(private http: HttpClient) {
  }

  getClientApplications(clientId: any): Promise<any> {
    // TODO - descomentar y borrar
    // return this.http.get<any[]>(`${this.url}/client/${clientId}/applications`).toPromise();

    return Promise.resolve(
      [
        {id: '0', date: Date.now(), state: 'En Almacen'},
      ]
    );
  }

  getClientProducts(clientId: any): Promise<any> {
    // TODO - descomentar y borrar
    // return this.http.get<any[]>(`${this.url}/client/${clientId}/products`).toPromise();

    return Promise.resolve(
      [
        {name: 'Pasta', date: Date.now(), lot: 123, quantity: 5},
        {name: 'Choclo', date: Date.now(), lot: 123, quantity: 5},
        {name: 'Caca', date: Date.now(), lot: 123, quantity: 5},
        {name: 'Asd', date: Date.now(), lot: 123, quantity: 5}
      ]
    );
  }

  createClientApplication(clientId: any, application: any): Promise<any> {
    // TODO - descomentar y borrar
    // return this.http.post<any[]>(`${this.url}/client/${clientId}/applications`, application).toPromise();

    return Promise.resolve(99);
  }

  getManagerApplications(): Promise<any> {
    // TODO - descomentar y borrar
    // return this.http.get<any[]>(`${this.url}/manager/applications`).toPromise();

    return Promise.resolve(
      [
        {
          id: '0', client: 'Velazquez', date: Date.now(), state: 'En Almacen', description: 'asd', products:
            [
              {name: 'Pasta', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Choclo', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Caca', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Asd', date: Date.now(), lot: 123, quantity: 5}
            ]
        },
        {
          id: '1', client: 'Bustamante', date: Date.now(), state: 'Aceptado', description: 'asd', products:
            [
              {name: 'Pasta', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Choclo', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Caca', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Asd', date: Date.now(), lot: 123, quantity: 5}
            ]
        },
        {
          id: '2', client: 'Curat', date: Date.now(), state: 'En Almacen', description: 'asd', products:
            [
              {name: 'Pasta', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Choclo', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Caca', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Asd', date: Date.now(), lot: 123, quantity: 5}
            ]
        },
        {
          id: '3', client: 'Curat', date: Date.now(), state: 'Nueva', description: 'asd', products:
            [
              {name: 'Pasta', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Choclo', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Caca', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Asd', date: Date.now(), lot: 123, quantity: 5}
            ]
        },
        {
          id: '4', client: 'Curat', date: Date.now(), state: 'Rechazada', description: 'asd', products:
            [
              {name: 'Pasta', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Choclo', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Caca', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Asd', date: Date.now(), lot: 123, quantity: 5}
            ]
        },
        {
          id: '5', client: 'Curat', date: Date.now(), state: 'Controlada', description: 'asd', products:
            [
              {name: 'Pasta', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Choclo', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Caca', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Asd', date: Date.now(), lot: 123, quantity: 5}
            ]
        },
        {
          id: '6', client: 'Curat', date: Date.now(), state: 'Controlada', description: 'asd', products:
            [
              {name: 'Pasta', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Choclo', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Caca', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Asd', date: Date.now(), lot: 123, quantity: 5}
            ]
        },
      ]
    );
  }

  getManagerApplication(id: any): Promise<any> {
    // TODO - descomentar y borrar
    // return this.http.get<any[]>(`${this.url}/manager/application/${id}`).toPromise();

    return Promise.resolve(
      {
        id: '0', client: 'Velazquez', date: Date.now(), state: 'En Almacen', description: 'hola', products:
          [
            {name: 'Pasta', date: Date.now(), lot: 123, quantity: 5},
            {name: 'Choclo', date: Date.now(), lot: 123, quantity: 5},
            {name: 'Caca', date: Date.now(), lot: 123, quantity: 5},
            {name: 'Asd', date: Date.now(), lot: 123, quantity: 5}
          ]
      }
    );
  }

  controlledManagerApplication(application: any): Promise<any> {
    // TODO - descomentar y borrar
    // return this.http.post<any[]>(`${this.url}/manager/application/${application.id}/controlled`, application).toPromise();

    return Promise.resolve(true);
  }

  acceptManagerApplication(id: any) {
    // TODO - descomentar y borrar
    // return this.http.post<any[]>(`${this.url}/manager/application/${application.id}/accept`).toPromise();

    return Promise.resolve(true);
  }

  denyManagerApplication(id: any) {
    // TODO - descomentar y borrar
    // return this.http.post<any[]>(`${this.url}/manager/application/${application.id}/deny`).toPromise();

    return Promise.resolve(true);
  }
}
