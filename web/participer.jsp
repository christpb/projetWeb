<%-- 
    Document   : participer
    Created on : 21 mai 2017, 15:11:32
    Author     : perle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="card center-align">
            <div class="card-content">
                <span class="card-title">Participer</span>
                <form class="s12" action="ServletUsers" method="post">
                    <div class="row">
                        <div class="input-field col s12">
                            <i class="material-icons prefix">perm_identity</i>
                            <input id="icon_nom" type="text" class="validate" name="nom">
                            <label for="icon_nom" class="">Nom</label>
                        </div>
                        <div class="input-field col s12">
                            <i class="material-icons prefix">perm_identity</i>
                            <input id="icon_prenom" type="text" class="validate" name="prenom">
                            <label for="icon_prenom" class="">Prénom</label>
                        </div>
                        <div class="input-field col s12">
                            <i class="material-icons prefix">email</i>
                            <input id="icon_email" type="text" class="validate" name="email">
                            <label for="icon_email" class="">Email</label>
                        </div>
                        <div class="input-field col s12">
                            <i class="material-icons prefix">lock</i>
                            <input id="icon_password" type="password" class="validate" name="password">
                            <label for="icon_password">Mot de passe</label>
                        </div>
                        <p>Vous êtes :</p>
                        <p>
                            <input name="group1" type="radio" id="miagiste"/>
                            <label for="miagiste">Miagiste</label>
                        </p>
                        <p>
                            <input name="group1" type="radio" id="enseignant" disabled="disabled"/>
                            <label for="enseignant">Enseignant</label>
                        </p>
                        <p>
                            <input class="with-gap" name="group1" type="radio" id="entreprise"/>
                            <label for="entreprise">Entreprise partenaire</label>
                        </p>
                        <div class="option_miagiste" id="option_miagiste" style="display: none">
                            <div class="input-field col s12">
                                <i class="material-icons prefix">perm_identity</i>
                                <input id="icon_date" type="date" class="validate" name="date">
                                <label for="icon_date" class="">Date de naissance</label>
                            </div>
                            <div class="input-field col s12">
                                <select>
                                    <option value="" disabled selected>Choisissez votre MIAGE :</option>
                                    <option value="1">Option 1</option>
                                    <option value="2">Option 2</option>
                                    <option value="3">Option 3</option>
                                </select>
                                <label>Materialize Select</label>
                            </div>
                            <div class="file-field input-field">
                                <div class="btn">
                                    <span>Photo</span>
                                    <input type="file">
                                </div>
                                <div class="file-path-wrapper">
                                    <input class="file-path validate" type="text">
                                </div>
                            </div>
                            <p>Miagiste diplomé :</p>
                            <p>
                                <input name="group1" type="radio" id="Ydiplome"/>
                                <label for="Ydiplome">Oui</label>
                            </p>
                            <p>
                                <input name="group1" type="radio" id="Ndiplome"/>
                                <label for="Ndiplome">Non</label>
                            </p>
                        </div>

                        <div class="option_entreprise" id="option_entreprise" style="display: none">
                            <div class="input-field col s12">
                                <i class="material-icons prefix">perm_identity</i>
                                <input id="icon_fonction" type="text" class="validate" name="fonction">
                                <label for="icon_fonction" class="">Fonction</label>
                            </div>
                            <div class="input-field col s12">
                                <i class="material-icons prefix">perm_identity</i>
                                <input id="icon_tel" type="text" class="validate" name="tel">
                                <label for="icon_tel" class="">Téléphone</label>
                            </div>
                            <div class="input-field col s12">
                                <i class="material-icons prefix">perm_identity</i>
                                <input id="icon_nomE" type="tel" class="validate" name="nomE">
                                <label for="icon_nomE" class="">Nom entreprise</label>
                            </div>
                            <div class="input-field col s12">
                                <i class="material-icons prefix">perm_identity</i>
                                <input id="icon_adrE" type="text" class="validate" name="adrE">
                                <label for="icon_adrE" class="">Adresse de l'entreprise</label>
                            </div>
                            <div class="input-field col s12">
                                <i class="material-icons prefix">perm_identity</i>
                                <input id="icon_sectE" type="text" class="validate" name="sectE">
                                <label for="icon_sectE" class="">Secteur d'activité de l'entreprise</label>
                            </div>
                        </div>


                    </div>
                </form>
                <input type="hidden" name="action" value="connexion"/>  
                <input class="btn waves-effect waves-light" type="submit" value="Connexion" name="submit"/>
            </div>
        </div>
    </body>
</html>
