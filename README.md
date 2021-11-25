# correctjava
# Correct-an-address

## Tehnologii folosite

- Spring Framework(Java) <br>
- React Framework(JavaScript, HTML, CSS) <br>

### Alte resurse

- Country State City Api: https://countrystatecity.in/

## Algoritmi implementati

Algoritmul de corectare adresa: <br>
&nbsp; La inceput, algoritmul preia din repositories tara, statele si orasele cu acelasi nume ca cele trimise in
request, sau in cazul tarilor, daca in request numele tarii nu are o lungime mai mare decat 2, se cauta dupa codul Iso2.
<br> &nbsp; Algoritmul incearca sa corecteze adresa doar daca gaseste cel putin 2/3 rezultate la interogari, daca nu
gaseste iese din functie si rezultatul obtinut este "Couldn't correct address". Daca reuseste sa gaseasca cel putin 2/3
match-uri, continua cu corectarea astfel:
<br>
 <ol> 
  <li> Se verifica daca orasul se afla in statul sau 
in tara obtinuta in urma 
interogarilor . Daca se gaseste un match, atunci se 
cauta(in functie de rezultat) statul sau tara cu id-ul din
oras. </li> 
    <ul> <li>Daca orasul nu se afla in tara sau in niciunul
dintre statele obtinute, atunci inseamna ca adresa
 ori nu poate fi corectata, ori orasul este cel gresit.
 In acest caz se verifica daca unul din state 
se afla in tara obtinuta. Daca nu, adresa nu poate fi corectata.
Altfel : adresa poate fi corectata, stim ca statul se afla in tara
obtinuta, se incearca un match cat mai mare lungimea literelor
orasului oferit. Daca nu se gaseste niciun match, algoritmul va 
returna un oras random din statul respectiv.
 
</li>
    </ul>
  <li> Daca orasul se afla in tara obtinuta, atunci se corecteaza
 statul pe baza id-ului din oras.</li>
  <li> Daca orasul se afla in statul obtinut dar tara este gresita,
 se corecteaza aceasta pe baza id-ului din state.</li>
  <li> Daca adresa este deja corecta, se intoarce aceasta, 
cu modificari de litere(daca este cazul, e.g. : rOmANia -> Romania).</li>
</ol>

In orice caz de nereusita a corectarii adresei, se returneaza
 raspunsul "Couldn't correct address!".

## Contributie:

### Autori:

- Balint Paula
    - Modelare proiect & algoritm
    - Research geo API
    - Hostare research
    - Testing
    - Incercare hostare Back End
- Bodnariu Daniel
    - Front end
    - Hostare Front End
    - Testing
    - Api connector(incluzand modelare pentru obtinerea informatiilor)
    - Backup baza de date
