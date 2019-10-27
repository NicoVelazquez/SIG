import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HttpRequestsService {

  // TODO - setear url para pegarle al back
  url = 'http://localhost:9000';

  currentUser: any;

  constructor(private http: HttpClient) {
    this.currentUser = JSON.parse(localStorage.getItem('user'));
  }

  signIn(email: string, password: string): Promise<any> {
    // TODO - descomentar y borrar
    const credentials = {email, password};
    return this.http.post<any[]>(`${this.url}/login`, credentials).toPromise();

    // return Promise.resolve({id: 1, role: 'client'});
  }

  // ---------------------------------------------------------------------------------------------------
  // CLIENT
  // ---------------------------------------------------------------------------------------------------

  getClientApplications(): Promise<any> {
    // TODO - descomentar y borrar
    // return this.http.get<any[]>(`${this.url}/client/${this.currentUser.id}/applications`).toPromise();

    return Promise.resolve(
      [
        {id: '0', date: Date.now(), state: 'Con Nota'},
      ]
    );
  }

  getClientProducts(): Promise<any> {
    // TODO - descomentar y borrar
    // return this.http.get<any[]>(`${this.url}/client/${this.currentUser.id}/products`).toPromise();

    return Promise.resolve(
      [
        {id: 0, name: 'Pasta', date: Date.now(), lot: 123, quantity: 5},
        {id: 1, name: 'Choclo', date: Date.now(), lot: 123, quantity: 5},
        {id: 2, name: 'Caca', date: Date.now(), lot: 123, quantity: 5},
        {id: 3, name: 'Asd', date: Date.now(), lot: 123, quantity: 5}
      ]
    );
  }

  createClientApplication(application: any): Promise<any> {
    // TODO - descomentar y borrar
    // return this.http.post<any[]>(`${this.url}/client/${this.currentUser.id}/applications`, application).toPromise();

    return Promise.resolve({id: 99});
  }

  deleteClientApplication(id: any) {
    // TODO - descomentar y borrar
    // return this.http.delete<any[]>(`${this.url}/client/${this.currentUser.id}/applications/${id}`).toPromise();

    return Promise.resolve(true);
  }

  // ---------------------------------------------------------------------------------------------------
  // MANAGER
  // ---------------------------------------------------------------------------------------------------

  getManagerApplications(): Promise<any> {
    // TODO - descomentar y borrar
    // return this.http.get<any[]>(`${this.url}/manager/applications`).toPromise();

    return Promise.resolve(
      [
        {
          id: '0', client: 'Velazquez', date: Date.now(), cost: 3, state: 'En Almacen', description: 'asd', products:
            [
              {name: 'Pasta', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Choclo', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Caca', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Asd', date: Date.now(), lot: 123, quantity: 5}
            ]
        },
        {
          id: '1', client: 'Bustamante', date: Date.now(), cost: 3, state: 'Aceptado', description: 'asd', products:
            [
              {name: 'Pasta', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Choclo', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Caca', date: Date.now(), lot: 123, quantity: 5},
              {name: 'Asd', date: Date.now(), lot: 123, quantity: 5}
            ]
        },
        {
          id: '2', client: 'Curat', date: Date.now(), cost: 3, state: 'En Almacen', description: 'asd',
          products: [
            {name: 'Pasta', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5},
            {name: 'Choclo', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5},
            {name: 'Caca', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5},
            {name: 'Asd', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5}
          ]
        },
        {
          id: '3', client: 'Curat', date: Date.now(), cost: 3, state: 'Nueva', description: 'asd',
          products: [
            {name: 'Pasta', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5},
            {name: 'Choclo', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5},
            {name: 'Caca', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5},
            {name: 'Asd', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5}
          ]
        },
        {
          id: '4', client: 'Curat', date: Date.now(), cost: 3, state: 'Rechazada', description: 'asd',
          products: [
            {name: 'Pasta', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5},
            {name: 'Choclo', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5},
            {name: 'Caca', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5},
            {name: 'Asd', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5}
          ]
        },
        {
          id: '5', client: 'Curat', date: Date.now(), cost: 3, state: 'Controlada', description: 'asd', observation: 'todo en orden',
          products: [
            {name: 'Pasta', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
            {name: 'Choclo', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
            {name: 'Caca', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
            {name: 'Asd', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3}
          ]
        },
        {
          id: '6', client: 'Curat', date: Date.now(), cost: 3, state: 'Controlada', description: 'asd', observation: 'todo en orden',
          products: [
            {name: 'Pasta', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
            {name: 'Choclo', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
            {name: 'Caca', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
            {name: 'Asd', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3}
          ]
        },
        {
          id: '7', client: 'Velazquez', date: Date.now(), cost: 3, state: 'Con Nota', description: 'asd', observation: 'todo en orden',
          products: [
            {name: 'Pasta', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
            {name: 'Choclo', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
            {name: 'Caca', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
            {name: 'Asd', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3}
          ]
        }
      ]
    );
  }

  getManagerApplication(id: any): Promise<any> {
    // TODO - descomentar y borrar
    // return this.http.get<any[]>(`${this.url}/manager/applications/${id}`).toPromise();

    return Promise.resolve(
      {
        id: '0', client: 'Velazquez', date: Date.now(), cost: 5, state: 'Nueva', description: 'hola', observation: 'todo en orden',
        products: [
          {name: 'Pasta', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
          {name: 'Choclo', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
          {name: 'Caca', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
          {name: 'Asd', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3}
        ]
      }
    );
  }

  controlledManagerApplication(application: any): Promise<any> {
    // TODO - descomentar y borrar
    // return this.http.post<any[]>(`${this.url}/manager/applications/${application.id}/controlled`, application).toPromise();

    return Promise.resolve(true);
  }

  acceptManagerApplication(id: any) {
    // TODO - descomentar y borrar
    // return this.http.post<any[]>(`${this.url}/manager/applications/${application.id}/accept`).toPromise();

    return Promise.resolve(true);
  }

  denyManagerApplication(id: any) {
    // TODO - descomentar y borrar
    // return this.http.post<any[]>(`${this.url}/manager/applications/${application.id}/deny`).toPromise();

    return Promise.resolve(true);
  }

  createManagerNote(application: any, note: any): Promise<any> {
    // TODO - descomentar y borrar
    const request = {note, application};
    console.log(request);
    // return this.http.post<any[]>(`${this.url}/manager/applications/${application.id}/note`, request).toPromise();

    return Promise.resolve(true);
  }

  getNote(applicationId: any): Promise<any> {
    // TODO - descomentar y borrar
    // return this.http.get<any[]>(`${this.url}/manager/applications/${applicationId}/note`).toPromise();

    return Promise.resolve(
      {
        note: {
          date: '2019-10-24',
          descriptionCredit: 'Ok',
          descriptionDebit: 'Algunos mal estado',
          priceCredit: 320,
          priceDebit: 40,
        },
        application: {
          id: '0', client: 'Velazquez', date: Date.now(), cost: 5, state: 'En Almacen', description: 'hola', observation: 'todo en orden',
          products: [
            {name: 'Pasta', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
            {name: 'Choclo', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
            {name: 'Caca', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
            {name: 'Asd', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3}
          ]
        }
      }
    );


  }
}
