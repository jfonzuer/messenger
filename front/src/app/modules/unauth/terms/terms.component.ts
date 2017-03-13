import {Component, OnInit, ViewContainerRef} from '@angular/core';
import { Overlay } from 'angular2-modal';
import {Modal, } from 'angular2-modal/plugins/bootstrap';

@Component({
  selector: 'app-terms',
  template: `
    J'accepte les <a (click)="openTerms()">conditions générales d'utilisation</a>
   `,
})
export class TermsComponent implements OnInit {

  constructor(vcRef: ViewContainerRef, public overlay: Overlay, public modal: Modal) {
    overlay.defaultViewContainer = vcRef;
  }

  ngOnInit() {
  }

  openTerms() {
    this.modal.alert()
      .size('lg')
      .showClose(true)
      .title('Condtions générales d\'utilisation')
      .body(`
      <h1>PRÉAMBULE</h1>
      Avant toute utilisation des services du site internet accessible à l’adresse www.dominapp.com (ci-après désigné par le « Site »), vous vous engagez à lire et à accepter sans réserve les présentes conditions générales d'utilisation (ci-après les « CGU »).
      
      <h1>ARTICLE 1 : DÉFINITIONS</h1>
      Abonnements : également désignés par « Pack », sont des contrats souscrits pour des périodes de 1, 3 ou 6 mois. Les Abonnements sont destinés aux Utilisateurs masculins et donnent accès à l’intégralité des services payants du Site. Les tarifs des Abonnements sont définis à l’article 3 des présentes CGU.
      
      Le site constitue un Service payant mis à la disposition des Utilisateurs masculins. L'abonnement premium permet à un Utilisateur masculin de contacter une Utilisatrice via son Compte, afin de solliciter une prise de contact via le Site.
      
      Compte Utilisateur : correspond à l’espace personnel de chaque Utilisateur sur le Site. Les Utilisateurs y accèdent via un mot de passe et un identifiant qui leur sont strictement personnels et confidentiels. Le Compte d’Utilisateur permet à l'Utilisateur de disposer d'une identité au sein du réseau social virtuel du Site et d'interagir avec les autres Utilisateurs sur le Site.
      
      Services : désignent les services payants ou gratuits fournis aux Utilisateurs du Site et ayant pour objet de favoriser les rencontres virtuelles entre personnes physiques à des fins personnelles, de loisirs et non commerciales. Les services payants sont décrits à l’article 2 des présentes CGU.
      
      Utilisateur : Internaute ayant adhéré aux présentes CGU. On distingue les « Utilisateurs masculins » (Hommes) des « Utilisatrices » (Femmes), « l'(les) Utilisateur(s) » désignant communément l'un ou l'autre.
      
      <h1>ARTICLE 2 : INSCRIPTION ET CONDITIONS D’ACCÈS AUX SERVICES DU SITE</h1>
      2.1 Objet des Services
      
      Le Site est un espace convivial destiné aux seuls Utilisateurs, créé pour faciliter les rencontres virtuelles entre hommes soumis et femmes dominatrices, en leur permettant de faire connaissance ou de s'amuser librement dans un contexte humoristique tout en développant leur réseau social.
      
      Chaque Utilisateur peut, librement et en toute confidentialité, communiquer et échanger informations et sentiments personnels afin de faire connaissance avec les autres Utilisateurs, dans les limites prévues aux présentes CGU.
      
      Les Services proposés par Dominapp ne sont pas des activités de conseil ou de courtage matrimonial et visent exclusivement à supporter et promouvoir le développement du réseau social virtuel des Utilisateurs sur le Site.
      
      2.2 Conditions pour s’inscrire
      
      Pour s’inscrire, il convient :
      
      D'être âgé d'au moins 18 ans ;
      D'être domicilié, de résider, ou d'être seulement situé sur le territoire français, en Belgique, au Luxembourg ou en Suisse ;
      De ne pas avoir été privé de ses droits civiques ;
      D'avoir lu et accepté les présentes CGU ;
      D'avoir rempli les champs obligatoires du formulaire d'inscription ;
      De ne pas avoir eu son Compte Utilisateur résilié plus de deux fois pour manquement aux présentes CGU ou aux lois en vigueur.
      L'Utilisateur garantit la sincérité et la véracité des informations communiquées, et s'engage à les tenir régulièrement à jour en y accédant par son Compte Utilisateur.
      
      L’engagement de l’Utilisateur est effectif par le fait de cocher la case « J'ai lu et j'accepte les conditions générales d'utilisation » située dans le formulaire d'inscription. À défaut d'un tel engagement, l’Utilisateur ne peut pas poursuivre son processus d'inscription et n’a pas accès aux Services.
      
      Dès validation de l’inscription par l’Utilisateur et acceptation des présentes CGU, un message de confirmation d’inscription est envoyé à l’adresse email renseignée par l’Utilisateur dans le formulaire de commande.
      
      2.3 Le Formulaire d'inscription
      
      L’inscription sur le Site est gratuite.
      L'Utilisateur fournit obligatoirement les informations ci-dessous:
      
      Date de naissance ;
      Pseudonyme (ou « Pseudo ») soit le nom d'Utilisateur souhaité ;
      Adresse e-mail ;
      Région ;
      Coordonnées bancaires en cas d’achat sur le Site ;
      Photo dite « de couverture » pour identifier l'Utilisateur, conformément aux dispositions de l'article 7.4.
      A titre optionnel, l'Utilisateur peut communiquer :
      
      A l'attention de Dominapp : ses nom et prénom.
      A l'attention des autres Utilisateurs : des informations sur son apparence physique, ses passions, sa profession et toute autre information personnelle dont l'Utilisateur souhaite assurer une diffusion publique auprès des internautes accédant au Site à l'exception :
      des coordonnées personnelles (numéro de téléphone, adresse e-mail...) dont la diffusion est expressément prohibée par Dominapp, à des fins de protection des Utilisateurs ;
      de liens hypertextes vers d'autres sites web ;
      de propos à connotation discriminatoire, injurieuse, agressive ou incitant à la commission d'une infraction.
      Ces informations pourront être modifiées à tout moment par l'Utilisateur dans les conditions prévues à l'article 7 des présentes CGU.
      
      Lorsque les conditions nécessaires à l'inscription sont remplies par l'Utilisateur, celui-ci se voit attribuer, à titre personnel, un Compte d’Utilisateur sur lequel figure notamment sa fiche de profil ainsi qu'un compte de messagerie, à partir duquel il peut interagir avec les autres Utilisateurs.
      
      L'Utilisateur est responsable de l'utilisation de ses éléments d'identification (identifiant et mot de passe) par des tiers, ainsi que des actions ou déclarations faites par l'intermédiaire de son Compte Utilisateur, et garantit Dominapp contre toute demande à ce titre.
      
      2.4 Conditions générales d’accès aux Services
      
      Les équipements (hardware, software...) et frais de télécommunication permettant d'accéder aux Services restent à la charge exclusive de l'Utilisateur.
      
      L’intégralité des Services du Site sont mis à disposition gratuitement pour les Utilisatrices.
      
      Les présentes CGU s’appliquent aux Utilisatrices du Site à l’exception des articles 3, 4.2 et 4.3.
      
      En revanche, pour les Utilisateurs masculins, la visualisation des informations relatives aux visites qu’il a reçues, l’accès à la messagerie se font exclusivement dans le cadre d’un Abonnement.
      
      Cependant, au lancement du site, pour une période de quelques mois, les nouvels auront un accès premium garantit pour découvrir le site.
      
      <h1>ARTICLE 3 : LES ABONNEMENTS</h1>
      3.1 Les Packs
      
      Le pack premium est facturé 19,90 euros TTC. Ce montant est prélevé au jour de la souscription.
      
      3.2 Modalité de paiement
      
      L’Utilisateur peut procéder au paiement des Services acquis sur le Site par l’intermédiaire des services de la société PAYPAL.
      
      La sécurité du paiement est assurée par Paypal.
      
      <h1>ARTICLE 4 : DURÉE ET RENOUVELLEMENT DES ABONNEMENTS</h1>
      4.1 Droit de rétractation
      
      Conformément à l'article L. 121-21 du Code de la consommation, toute souscription à un Abonnement pourra être remboursée sans pénalité sur simple demande non motivée dans un délai de 14 jours à compter de ladite souscription par l'envoi d'un courriel adressé à dominapp.contact@gmail.com avec, en pièce jointe, la facture d’achat reçue du service Paypal. Il ne sera procédé qu'à un seul remboursement par RIB et par Compte d’Utilisateur.
      
      A réception de l'ensemble de ces documents, l'Utilisateur sera remboursé de l'intégralité des sommes versées au Site dans un délai de 10 jours à compter de la réception d'un courriel. Il est rappelé qu’en application des dispositions de l'article Article L121-21-8 du Code de la Consommation, l'Utilisateur ne pourra plus exercer son droit de rétractation après utilisation des Services (payants et/ou gratuits).
      
      4.2 Renouvellement automatique
      
      A son expiration, l'Abonnement en cours est renouvelé pour une durée équivalente à celle initialement souscrite par l’Utilisateur. L’Utilisateur peut s’opposer à cette reconduction en modifiant les paramètre de l'abonnement souscrit auprès de paypal.
      
      À défaut d’une telle notification, l'Abonnement est reconduit et sera facturé sur la même base tarifaire et selon des périodicités identiques à celle de l'Abonnement initialement souscrit.
      
      Conformément à l'article L. 136-1 du Code de la consommation reproduit ci-après, Dominapp adressera à l'Utilisateur un courriel l'informant de sa faculté de ne pas reconduire l'Abonnement et les modalités de non-reconduction dudit Abonnement. Il l'informera également de son obligation de paiement du nouvel Abonnement.
      
      "Le professionnel prestataire de services informe le consommateur par écrit, par lettre nominative ou courrier électronique dédiés, au plus tôt trois mois et au plus tard un mois avant le terme de la période autorisant le rejet de la reconduction, de la possibilité de ne pas reconduire le contrat qu'il a conclu avec une clause de reconduction tacite. Cette information, délivrée dans des termes clairs et compréhensibles, mentionne, dans un encadré apparent, la date limite de résiliation.
      
      Lorsque cette information ne lui a pas été adressée conformément aux dispositions du premier alinéa, le consommateur peut mettre gratuitement un terme au contrat, à tout moment à compter de la date de reconduction. Les avances effectuées après la dernière date de reconduction ou, s'agissant des contrats à durée indéterminée, après la date de transformation du contrat initial à durée déterminée, sont dans ce cas remboursées dans un délai de trente jours à compter de la date de résiliation, déduction faite des sommes correspondant, jusqu'à celle-ci, à l'exécution du contrat. A défaut de remboursement dans les conditions prévues ci-dessus, les sommes dues sont productives d'intérêts au taux légal.
      
      Les dispositions du présent article s'appliquent sans préjudice de celles qui soumettent légalement certains contrats à des règles particulières en ce qui concerne l'information du consommateur.
      
      Les trois alinéas précédents ne sont pas applicables aux exploitants des services d'eau potable et d'assainissement. Ils sont applicables aux consommateurs et aux non-professionnels."
      
      4.3 Désactivation de Compte
      
      L'Utilisateur peut désactiver son profil via son Compte Utilisateur s'il ne souhaite pas être visible sur le Site par les autres Utilisateurs.
      
      Lors de la Désactivation du Compte Utilisateur :
      
      le Profil de l'Utilisateur ne sera plus visible pour les autres Utilisateurs.
      en se connectant grâce à ses identifiants, l’Utilisateur réactive son Compte Utilisateur. En l'absence de connexion au compte celui-ci reste désactivé et ne permet pas à l'Utilisateur d'interagir avec les autres membres du Site.
      En cas de réactivation du Compte d’Utilisateur, les données conservées lui seront réaffectées.
      La désactivation ne supprime pas le Compte de l’Utilisateur ni ne libère l’Utilisateur de ses obligations.
      
      <h1>ARTICLE 5 : OBLIGATIONS ET RESPONSABILITÉ DES UTILISATEURS</h1>
      5.1 Obligations
      
      Les Utilisateurs s'engagent lors de la souscription au Site :
      
      A être honnête et sincère dans leurs déclarations ;
      A respecter les lois et règlements en vigueur et à ne pas enfreindre l'ordre public ;
      A respecter les droits de propriété intellectuelle ou industrielle ;
      A ne pas mettre en ligne de photos d'un tiers ;
      A ne pas commettre de délits de diffamation, d'injure, de provocations notamment à caractère racial ou discriminatoire, d'apologies de crime de guerre ou de crime contre l'humanité ;
      A ne pas communiquer leur mot de passe et autres codes confidentiels permettant à des tiers ou à d'autres Utilisateurs d'accéder aux Services de leur Compte Utilisateur ;
      A ne pas utiliser de logiciel, d'application, d'interface ou l'aide d'un tiers afin d'interférer dans la prise de contact avec les autres Utilisateurs (Utilisatrices) du site (la crédibilité du Site reposant sur la réalité des échanges) ;
      A respecter la confidentialité des correspondances et la vie privée des autres Utilisateurs;
      A ne pas diffuser ou divulguer le contenu des correspondances et messages qui leur ont été directement adressés dans le cadre des Services mis à leur disposition ;
      A s'abstenir envers les autres Utilisateurs de toute proposition ou initiative contraire à leur volonté exprimée ;
      A demeurer courtois et correct envers les autres Utilisateurs ;
      A ne pas porter atteinte aux Services ou au Site ;
      A ne pas proposer, solliciter ou promouvoir de biens ou de services payants ou de contreparties financières, la prostitution étant formellement interdite sur le Site ;
      A ne pas détourner les utilisateurs de ce Site vers un autre site (notamment à l'aide de liens hypertextes), ou un service concurrent ;
      A s'abstenir de toute utilisation commerciale ou publicitaire du Site ;
      A ne pas organiser de manifestation, réunion collective de ses membres au moyen du Site et des Services mis à leur disposition.
      5.2 Responsabilité
      
      L'Utilisateur est seul responsable de l'usage des données qu'il consulte, interroge et transfère sur le Site.
      Dans le cas où la responsabilité de Dominapp serait recherchée à raison d'un manquement par un Utilisateur aux obligations qui lui incombent au terme de la loi ou des CGU, ce dernier s'engage à garantir Dominapp ontre toute condamnation prononcée à son encontre trouvant son origine dans le manquement imputé à l'Utilisateur.
      Dominapp n'exerce aucun contrôle des sites et sources externes (pages web, forums...) vers lesquels redirigent les liens hypertextes mis en ligne par les Utilisateurs sur le Site et ne saurait être tenu responsable de leur contenu. A ce titre, les Utilisateurs sont invités à interrompre leur consultation et à alerter Dominapp en cas de découverte d'un tel lien hypertexte pointant vers un site ou une source externe dont le titre et ou les contenus constituent une violation du droit français.
      
      <h1>ARTICLE 6 : OBLIGATIONS ET RESPONSABILITÉ de Dominapp</h1>
      6.1 Obligations
      
      Dominapp s'engage à fournir aux Utilisateurs un service en ligne conforme aux règles de l'art et à la législation.
      
      La Configuration exigée pour bénéficier des Services du Site est :
      
      Activation de Javascript,
      Acceptation des cookies,
      Acceptation de l'affichage des fenêtres pop-up
      Nous recommandons de télécharger la version la plus récente de votre navigateur Internet afin de profiter au mieux de Dominapp.com.
      
      Toutefois, en cas de suspension de l'accès aux Services due à une panne informatique ou en cas d'impossibilité d'utiliser les Services du fait de Dominapp pendant la durée de l'Abonnement, celui-ci sera prorogé de plein droit au profit de l'Utilisateur pour une durée équivalente à celle de la suspension des Services.
      
      6.2 Responsabilité
      
      Dominapp n'est pas responsable des cas d'escroquerie, d'usurpation d'identité et autres infractions pénales commises par des Utilisateurs au moyen du Site et des Services mis à leur disposition, ou d'atteintes à l'image ou à la vie privée d'un tiers commises dans les mêmes conditions.
      
      Dominapp se réserve le droit de sauvegarder tout contenu litigieux, notamment pour le mettre à disposition des autorités compétentes.
      
      Dominapp n'est pas responsable des rencontres « réelles » organisées par les Utilisateurs. Il est rappelé aux Utilisateurs qui souhaiteraient organiser de tels rendez-vous sous leur seule responsabilité, qu'Dominapp n'est pas en mesure de garantir l'identité des Utilisateurs rencontrés sur le Site : il est donc nécessaire de prendre des précautions d'usage lors de telles rencontres physiques (avertir un proche de ce rendez-vous, choisir un lieu public...).
      
      Dominapp prohibe toute organisation par les Utilisateurs ou par des tiers de manifestation(s), réunion(s) collective(s) de ses membres au moyen du Site et des Services mis à disposition des Utilisateurs. A ce titre Dominapp ne saurait être tenu responsable d'un trouble à l'ordre public ou des dommages causés par les Utilisateurs et les tiers ayant participé à une telle réunion.
      
      Dominapp ne garantit pas que les Services seront utilisables en cas de défaillance du Fournisseur d'accès Internet de l'Utilisateur, ou en cas de non fonctionnement ou de mauvaises conditions d'accès provoquées par l'encombrement du réseau internet et toutes autres raisons extérieures à Dominapp et ses prestataires, et ayant un caractère de force majeure tel que défini par les juridictions françaises.
      
      <h1>ARTICLE 7 : VIE PRIVÉE</h1>
      Dominapp attache la plus grande importance à protéger la vie privée de ses Utilisateurs et de ses visiteurs.
      
      Les Utilisateurs sont invités à signaler tout dysfonctionnement et notamment tout manquement à leurs droits (à titre d’exemple : diffusion de leurs photos par des tiers sur notre Site) via la rubrique « Contact », accessible directement depuis la page d’accueil de notre site, ou via les adresses notamment courriel précisées ci-après.
      
      
      Les informations ainsi collectées sont destinées à être utilisées exclusivement par Dominapp et ses prestataires. Toute diffusion à des tiers des données personnelles recueillies sur les Utilisateurs fera l'objet d'une demande expresse et préalable.
      
      Les informations enregistrées par les Utilisateurs sont accessibles aux services de la société situés en France ainsi qu'à ses prestataires situés dans et hors de l'Union Européenne.
      
      La transmission de ces données au sous traitant du Site situé en dehors de l’Union Européenne n’est destinée qu’au traitement des questions générales pouvant être posées par les Utilisateurs (date de souscription ou de création du profil, date de paiement de l’abonnement, date de renouvellement et montants réglés ou remboursés) et à veiller au respect, par les membres du Site, de la législation et des présentes CGU.
      
      Le transfert de données est encadré par les clauses contractuelles types établies par la Commission Européenne.
      
      L’acceptation du traitement des données mentionnées ci-avant par nos prestataires situés dans ou en dehors du territoire de l’Union Européenne est une condition essentielle à l’utilisation des Services, gratuits ou payants, du Site.
      
      En consentant aux présentes CGU l’Utilisateur accepte que ces données (date de souscription ou de création du profil, date de paiement de l’abonnement, date de renouvellement et montants réglés ou remboursés) puissent être traitées dans les conditions ci-avant.
      
      Conformément aux articles 39 et suivants de la loi n° 78-17 du 6 janvier 1978 modifiée en 2004 relatives à l’informatique, aux fichiers et aux libertés, toute personne peut obtenir communication et, le cas échéant, rectification ou suppression des informations la concernant, en s’adressant au Support Clientèle du Site à l’adresse suivante : support@Dominapp.com
      
      7.2 Cookies
      
      Les cookies sont utilisés pour faciliter la navigation sur le Site. Ils stockent les informations personnelles que l'Utilisateur a saisies durant sa visite, à savoir :
      
      Nous stockons certaines informations en utilisant les "cookies". Un cookie est un morceau de données stockées sur votre ordinateur lié à l'utilisation de votre compte. Nous utilisons des cookies de session pour s'assurer que les utilisateurs sont connectés et pour stocker des informations de mot de passe.
      Ils stockent également les informations relatives à la navigation de l'Utilisateur sur le Site (les pages consultées, la date et l'heure de la consultation, etc.). Ainsi, lors des prochaines visites, l'Utilisateur n'aura pas à les saisir à nouveau.
      
      La durée de conservation des cookies est d’un mois.
      
      L'Utilisateur peut s'opposer à l'enregistrement de cookies en configurant son navigateur de la manière suivante :
      
      Pour Google Chrome :
      choisissez le menu "Paramètres" puis cliquez sur "Afficher les paramètres avancés" ;
      cliquez sur le bouton "Paramètres de contenu" dans la rubrique "Confidentialité" ;
      dans la rubrique "Cookie", sélectionnez les options qui vous conviennent.
      Pour Microsoft Internet Explorer :
      choisissez le menu "Outils" puis "Options Internet" ;
      cliquez sur l'onglet "Confidentialité" ;
      sélectionnez le niveau souhaité à l'aide du curseur.
      Pour Mozilla Firefox :
      choisissez le menu "Outils" puis "Options" ;
      cliquez sur l'icône "Vie privée" ;
      repérez le menu "cookie" et sélectionnez les options qui vous conviennent.
      Pour Opera 6 et au-delà :
      choisissez le menu "Fichier" > "Préférences" ;
      Vie Privée.
      7.3 Données personnelles collectées par Dominapp
      
      Lorsqu'il s'inscrit sur le Site, l'Utilisateur est amené à fournir des informations le concernant conformément à l'article 2.3.
      
      Les données personnelles ainsi collectées ont un caractère public ou privé en fonction de leur nature.
      
      Les données privées sont accessibles par certains salariés de Dominapp, strictement habilités à cet effet ; tandis que les données publiques sont accessibles à tous les Utilisateurs ainsi qu’à tous les internautes notamment depuis un moteur de recherche.
      
      Sont considérées comme des données personnelles à caractère privé, et dans ce cadre protégées :
      
      Les correspondances échangées entre les Utilisateurs ;
      Le numéro de téléphone portable communiqué pour recevoir les alertes par SMS ;
      L'adresse e-mail ;
      L'adresse postale ;
      Le nom et le prénom de l'Utilisateur.
      les coordonnées de la carte bancaire (même modifiées par un algorithme) ;
      la date de péremption de la carte bancaire.
      Sont considérées comme des données personnelles à caractère public et dans ce cadre accessibles à l’ensemble des Utilisateurs du Site et accessible via un moteur de recherche:
      
      Le Pseudo de l'Utilisateur et toutes données publiées sur le profil par l'Utilisateur, quelle que soit leur nature ;
      Toutes les données facultatives (critères de recherches...) ;
      Les photos et contenus mis en ligne par l'Utilisateur.
      L’ensemble des données personnelles à caractère public à l’exception des éléments de la rubrique Sexo, l’identifiant, le Pseudo, les grandes photos publiées par les Utilisateurs sont accessibles notamment via un moteur de recherche. Il appartient à l’Utilisateur de paramétrer son Compte d’Utilisateur à partir de la rubrique « Mon Compte » afin que les éléments de son Profil, à l’exception de ceux listés ci-avant, ne soient pas accessibles depuis un moteur de recherche.
      
      Les informations publiques sont susceptibles de révéler l'origine ethnique de l'Utilisateur, sa nationalité, ou sa religion et/ou ses orientations sexuelles. En fournissant de telles informations facultatives, l'Utilisateur manifeste son souhait de les voir publier et diffuser sur le Site et par conséquent son consentement explicite au traitement de données dites « sensibles » par Dominapp et en prend librement la responsabilité exclusive.
      
      Les données personnelles à caractère public fournies par les Utilisateurs peuvent notamment être utilisées par Dominapp dans le cadre des évènements destinés à promouvoir le Site.
      
      7.4 Photos
      
      Lors de son inscription, l'Utilisateur accepte la publication de ses photos sur le Site. L’Utilisateur est informé que les photos de profil sont des données personnelles à caractère public accessibles à tous les membres et Utilisateurs du Site.
      L’Utilisateur pourra à tout moment décider de modifier ladite photo dans les conditions prévues à l'article 7.1.
      
      Les photos doivent obligatoirement représenter l'Utilisateur. Les photos « topless » ou dénudées sont interdites.De même la publication et diffusion de photos de tiers, sur le Site est strictement interdite. L’Utilisateur publiant les photos d’un tiers pourra être sanctionné dans les conditions prévues à l’article 9 ci-après.
      
      7.5 Suppression automatique des conversations
      
      Au terme d'une période maximale d'un an, le Site procèdera à la suppression automatique et définitive de tout message supprimé ou placé dans la rubrique Spams par les Utilisateurs (destinataires et/ou expéditeurs).
      Les Utilisateurs sont informés que les messages supprimés en application du présent article ne pourront en aucun cas être restaurés par le Site.
      
      <h1>ARTICLE 8 : PROPRIÉTÉ INTELLECTUELLE</h1>
      Les graphismes, les photographies, les animations, les vidéos et les textes contenus sur le Site et plus globalement les éléments permettant d'assurer ou de faciliter la navigabilité au sein du Site - architecture, design, pages de code, pages CSS, et autres éléments - sont la propriété intellectuelle exclusive de Dominapp et ne peuvent être reproduits, utilisés ou représentés sous quelque forme que ce soit, sur quelque support que ce soit et par quelque media que ce soit, sans l'autorisation expresse de Dominapp , sous peine de poursuites judiciaires.
      Les droits d'utilisation concédés par Dominapp à l'Utilisateur sont réservés à un usage privé et personnel dans le cadre et pour la durée de l'Abonnement. Toute autre utilisation par l'Utilisateur est interdite sans l'autorisation de Dominapp. Toute autre utilisation est interdite sans l'autorisation de Dominapp. L'Utilisateur s'interdit notamment de modifier, copier, reproduire, télécharger, diffuser, transmettre, exploiter commercialement et/ou distribuer de quelque façon que ce soit les Services, les pages du Site, ou les codes informatiques des éléments composant les Services et le Site.
      L’Utilisateur concède à Dominapp une licence d'utilisation des droits de propriété intellectuelle attachés aux contenus publics fournis par ce même Utilisateur, aux fins de diffusion sur le Site. Cette licence comprend notamment, sous réserve des dispositions de l’article 7.1, le droit pour Dominapp de reproduire, représenter, adapter, traduire, numériser, utiliser aux fins du Service ou de sous-licencier les informations à caractère public concernant l’Utilisateur (élément du Profil, informations, images, vidéos, description, critères de recherche, etc.) sur tout ou partie du Service Dominapp (sur le Site, par courriel…) et/ou dans les mailings d’Dominapp et de manière générale sur tous supports de communication électronique (courriel, SMS, MMS, WAP, Internet) dans le cadre du Service. A ce titre, l’Utilisateur autorise expressément Dominapp à modifier lesdits contenus afin de respecter la charte graphique des Services Dominapp ou des autres supports de communication visés ci-dessus et/ou de les rendre compatibles avec ses performances techniques ou les formats des supports concernés. Ces droits sont concédés pour toute la durée d'exécution des présentes CGU entre l’Utilisateur et Dominapp. L’Utilisateur s'interdit de copier, reproduire, ou utiliser les contenus relatifs aux autres Utilisateurs.
      
      <h1>ARTICLE 9 : SANCTIONS, SUSPENSION D’ACCÈS AU COMPTE ET RÉSILIATION</h1>
      9.1 Points Boulet/Points Fake et Badges
      
      Tout Utilisateur importuné ou témoin du non-respect des présentes CGU par un autre Utilisateur doit le signaler au plus tôt en sélectionnant les fiches desdits Utilisateurs ou la rubrique « Contact » afin de cliquer sur les mentions « Signaler comme Fake » ou « Ce mec est un boulet ! ».
      En adhérant aux présentes CGU, l'Utilisateur reconnait que l'attribution de tels points relève d'un système de modération communautaire humoristique qui ne saurait à ce titre, caractériser une injure à son encontre.
      Tout Utilisateur obtenant trop de points « boulet » ou de points « fake » pourra être exclu dans les conditions prévues à l'article 9.2 des Conditions d'Utilisation. Dominapp adresse un avertissement par courriel à chaque Utilisateur ayant obtenu un nombre de points « boulet » ou points « fake » positif, l'informant que son Abonnement peut être résilié de plein droit et automatiquement. Les avertissements seront au nombre de 3, le troisième étant suivi de la clôture de son Compte Utilisateur sans que celui-ci puisse prétendre à un remboursement ou à une indemnisation de son préjudice.
      Les points « boulet » ou « fake » de l'Utilisateur ne sont jamais remis à zéro. Les points sont conservés durant toute la durée de vie du Compte Utilisateur, même si celui-ci aura été désactivé puis réactivé.
      Tout abus dans l'usage ce système de modération communautaire sera considéré comme une violation des présentes CGU pouvant donner lieu à une suspension d'accès au Compte Utilisateur ou à la résiliation définitive de l'Abonnement ainsi qu'à des poursuites judiciaires et à l'indemnisation des victimes d'un tel comportement, sans préjudice des dommages et intérêts pouvant être réclamés par Dominapp.
      
      Les Badges sont quant à eux attribuer à l’ensemble des Utilisateurs selon leur activité sur le Site et relèvent d'un système de gratification communautaire humoristique, qui ne saurait en aucun cas, caractériser une injure.
      
      9.2 Suspension d'accès au Compte
      
      En cas de non-respect des présentes CGU et notamment en cas de défaut de paiement par un Utilisateur, Dominapp pourra suspendre, à tout moment, l'accès à son Compte pendant une durée maximale d'un mois.
      En cas de suspension de l’accès au Compte, l’Utilisateur reste tenu de ses obligations de paiement vis-à-vis d’Dominapp aucun remboursement d’Abonnement et aucune indemnité ne sera accordée à l’Utilisateur.
      En cas de poursuite de la violation ou en l’absence de communication des justificatifs demandés par Dominapp, la suspension pourra être reconduite sur une nouvelle durée d'un mois dans une limite de trois mois maximum. Si un Compte est suspendu plus de 3 fois, Dominapp pourra le supprimer définitivement. Dans ce cas, aucun remboursement ne sera effectué.
      Dominapp n'a pas pour obligation de vérifier la réalité de la prétendue identité de ses Utilisateurs et ne dispose pas des moyens techniques et/ou légaux le lui permettant.
      Néanmoins, en cas de doute sur la réalité des informations divulguées par l'Utilisateur (et notamment sur l'âge), ou en cas de dénonciation par un tiers ou par un autre Utilisateur d'éléments laissant présumer l'existence d'une usurpation d'identité ou l'existence d'informations fictives délivrées par l'Utilisateur quant à son identité, Dominapp se réserve le droit de :
      
      suspendre l'accès au Compte Utilisateur afin d'effectuer les vérifications nécessaires ;
      demander une photocopie d'une pièce d'identité officielle et valide à l'Utilisateur mis en cause,
      bannir l’adresse IP ayant servi à la création d’un Compte d’Utilisateur litigieux.
      En cas de non-communication du justificatif susmentionné dans les 8 jours suivant la demande, ou en cas d'usurpation d'identité avérée ou d'informations fictives pouvant porter préjudice à un tiers, l'Utilisateur sera considéré comme contrevenant aux CGU et son Compte pourra être résilié dans les conditions prévues à l'article 9.3
      
      9.3 Résiliation
      
      L'Abonnement pourra être résilié de plein droit et sans délai en cas de manquement grave par l’Utilisateur aux présentes CGU, en cas de diffusion de contenu contraire à l'ordre public ou aux bonnes mœurs et en cas de non-paiement de l’Abonnement.
      De même, dans le cas où l’infraction signalée est grave, Dominapp pourra supprimer tous les comptes crées à partir de l’IP problématique.
      L'Utilisateur peut également demander la résiliation de son contrat et par conséquent de son Abonnement par mail. La résiliation demandée sera effectivement à l'expiration de l'Abonnement en cours
      
      9.5 Sort du Compte Utilisateur résilié
      
      Le Compte résilié est définitivement détruit. L’Utilisateur ne pourra plus accéder à son Compte ni utiliser les Services du Site. De même le Compte ne sera plus visible pour les autres Utilisateurs.
      Le Compte étant détruit, les données renseignées par l'Utilisateur ne pourront plus être communiquées à ce dernier sous réserve des dispositions du décret 2011-219 du 25 février 2011 relatif à la conservation et à la communication des données permettant l’identification de toute personne ayant contribuée à la création d’un contenu en ligne.
      
      <h1>ARTICLE 10 : MODIFICATION DES CONDITIONS D'UTILISATION</h1>
      Dominapp pourra modifier ou compléter les présentes CGU.
      Elles seront applicables aux Utilisateurs lors de la souscription d’un Abonnement et, elles rentreront en vigueur envers les Utilisateurs en cours d'Abonnement lors de la reconduction tacite de cet Abonnement.
      Les Utilisateurs en cours d'Abonnement seront avertis des nouvelles Conditions d'Utilisation en vigueur sur leur Compte Utilisateur, dans les termes prévus à l'article 4.2.
      Toutes modifications remplacent et annulent les précédentes stipulations dès lors qu'elles ont été acceptées par les Utilisateurs.
      
      <h1>ARTICLE 11 : DROIT APPLICABLE/ JURIDICTIONS COMPÉTENTES</h1>
      Les présentes CGU sont soumises au droit français, la langue d'interprétation des présentes CGU est la langue française. Tout litige né de l'exécution des présentes CGU ou en relation avec les présentes CGU sera soumis aux juridictions compétentes sur le territoire français.
      Si une ou plusieurs stipulations des présentes CGU étai(en)t déclarée(s) nulle(s) en application d'une loi, d'un règlement ou à la suite d'une décision définitive d'une juridiction compétente, les autre stipulations garderont toute leur force et leur portée sous réserve que les présentes CGU ne soient pas dénaturées, et que cela n'entraîne pas un déséquilibre significatif des obligations à la charges des parties.
      Le fait pour une partie de ne pas se prévaloir d'un manquement de l'autre partie à l'une des quelconques dispositions des présentes CGU ne saurait s'interpréter comme une renonciation de sa part à se prévaloir dans l'avenir d'un tel manquement.
      `)
      .open();
  }
}
