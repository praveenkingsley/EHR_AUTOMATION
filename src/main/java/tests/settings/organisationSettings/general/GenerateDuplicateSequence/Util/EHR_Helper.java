package tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util;

import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.Util.Report.SoftAssert;
import data.EHR_Data;
import data.Settings_Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.v85.page.Page;
import org.openqa.selenium.remote.CapabilityType;
import pages.commonElements.CommonActions;
import pages.commonElements.navbar.Page_Navbar;
import pages.login.Page_Login;
import pages.settings.Page_Settings;
import pages.settings.organisationSettings.general.Page_SequenceManager;
import pages.store.Page_Store;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class EHR_Helper extends ParallelTestBase {
    private static final Logger logger = LogManager.getLogger();

    public static void login(SoftAssert m_assert, WebDriver driver, String expectedLoggedInUser) {
        try {
            Page_Login oPage_Login = new Page_Login(driver);
            EHR_Data oEHR_Data = new EHR_Data();

            boolean notLoggedIn = waitForElementToBeDisplayed(oPage_Login.login_username, 3);

            String[] inputDataForLogin = EHR_Data.list_userName.get(expectedLoggedInUser).split(",");
            String userName = inputDataForLogin[0];
            String password = inputDataForLogin[1];

            if (notLoggedIn) {
                oPage_Login.login_username.click();
                Cls_Generic_Methods.clearValuesInElement(oPage_Login.login_username);
                Cls_Generic_Methods.customWait(1);
                oPage_Login.login_username.sendKeys(userName);
                m_assert.assertInfo("Entered Username : " + userName);

                oPage_Login.login_password.click();
                Cls_Generic_Methods.clearValuesInElement(oPage_Login.login_password);
                Cls_Generic_Methods.customWait(1);
                oPage_Login.login_password.sendKeys(password);
                m_assert.assertInfo("Entered Password : " + password);


                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Login.login_button), "Clicked on Login Button");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final Random RANDOM = new Random();
    private static final String[] LAST_NAMES = {"Aaran", "Aaren", "Aarez", "Aarman", "Aaron", "Aaron-James", "Aarron", "Aaryan", "Aaryn", "Aayan", "Aazaan", "Abaan", "Abbas", "Abdallah", "Abdalroof", "Abdihakim", "Abdirahman", "Abdisalam", "Abdul", "Abdul-Aziz", "Abdulbasir", "Abdulkadir", "Abdulkarem", "Abdulkhader", "Abdullah", "Abdul-Majeed", "Abdulmalik", "Abdul-Rehman", "Abdur", "Abdurraheem", "Abdur-Rahman", "Abdur-Rehmaan", "Abel", "Abhinav", "Abhisumant", "Abid", "Abir", "Abraham", "Abu", "Abubakar", "Ace", "Adain", "Adam", "Adam-James", "Addison", "Addisson", "Adegbola", "Adegbolahan", "Aden", "Adenn", "Adie", "Adil", "Aditya", "Adnan", "Adrian", "Adrien", "Aedan", "Aedin", "Aedyn", "Aeron", "Afonso", "Ahmad", "Ahmed", "Ahmed-Aziz", "Ahoua", "Ahtasham", "Aiadan", "Aidan", "Aiden", "Aiden-Jack", "Aiden-Vee", "Aidian", "Aidy", "Ailin", "Aiman", "Ainsley", "Ainslie", "Airen", "Airidas", "Airlie", "AJ", "Ajay", "A-Jay", "Ajayraj", "Akan", "Akram", "Al", "Ala", "Alan", "Alanas", "Alasdair", "Alastair", "Alber", "Albert", "Albie", "Aldred", "Alec", "Aled", "Aleem", "Aleksandar", "Aleksander", "Aleksandr", "Aleksandrs", "Alekzander", "Alessandro", "Alessio", "Alex", "Alexander", "Alexei", "Alexx", "Alexzander", "Alf", "Alfee", "Alfie", "Alfred", "Alfy", "Alhaji", "Al-Hassan", "Ali", "Aliekber", "Alieu", "Alihaider", "Alisdair", "Alishan", "Alistair", "Alistar", "Alister", "Aliyaan", "Allan", "Allan-Laiton", "Allen", "Allesandro", "Allister", "Ally", "Alphonse", "Altyiab", "Alum", "Alvern", "Alvin", "Alyas", "Amaan", "Aman", "Amani", "Ambanimoh", "Ameer", "Amgad", "Ami", "Amin", "Amir", "Ammaar", "Ammar", "Ammer", "Amolpreet", "Amos", "Amrinder", "Amrit", "Amro", "Anay", "Andrea", "Andreas", "Andrei", "Andrejs", "Andrew", "Andy", "Anees", "Anesu", "Angel", "Angelo", "Angus", "Anir", "Anis", "Anish", "Anmolpreet", "Annan", "Anndra", "Anselm", "Anthony", "Anthony-John", "Antoine", "Anton", "Antoni", "Antonio", "Antony", "Antonyo", "Anubhav", "Aodhan", "Aon", "Aonghus", "Apisai", "Arafat", "Aran", "Arandeep", "Arann", "Aray", "Arayan", "Archibald", "Archie", "Arda", "Ardal", "Ardeshir", "Areeb", "Areez", "Aref", "Arfin", "Argyle", "Argyll", "Ari", "Aria", "Arian", "Arihant", "Aristomenis", "Aristotelis", "Arjuna", "Arlo", "Armaan", "Arman", "Armen", "Arnab", "Arnav", "Arnold", "Aron", "Aronas", "Arran", "Arrham", "Arron", "Arryn", "Arsalan", "Artem", "Arthur", "Artur", "Arturo", "Arun", "Arunas", "Arved", "Arya", "Aryan", "Aryankhan", "Aryian", "Aryn", "Asa", "Asfhan", "Ash", "Ashlee-jay", "Ashley", "Ashton", "Ashton-Lloyd", "Ashtyn", "Ashwin", "Asif", "Asim", "Aslam", "Asrar", "Ata", "Atal", "Atapattu", "Ateeq", "Athol", "Athon", "Athos-Carlos", "Atli", "Atom", "Attila", "Aulay", "Aun", "Austen", "Austin", "Avani", "Averon", "Avi", "Avinash", "Avraham", "Awais", "Awwal", "Axel", "Ayaan", "Ayan", "Aydan", "Ayden", "Aydin", "Aydon", "Ayman", "Ayomide", "Ayren", "Ayrton", "Aytug", "Ayub", "Ayyub", "Azaan", "Azedine", "Azeem", "Azim", "Aziz", "Azlan", "Azzam", "Azzedine", "Babatunmise", "Babur", "Bader", "Badr", "Badsha", "Bailee", "Bailey", "Bailie", "Bailley", "Baillie", "Baley", "Balian", "Banan", "Barath", "Barkley", "Barney", "Baron", "Barrie", "Barry", "Bartlomiej", "Bartosz", "Basher", "Basile", "Baxter", "Baye", "Bayley", "Beau", "Beinn", "Bekim", "Believe", "Ben", "Bendeguz", "Benedict", "Benjamin", "Benjamyn", "Benji", "Benn", "Bennett", "Benny", "Benoit", "Bentley", "Berkay", "Bernard", "Bertie", "Bevin", "Bezalel", "Bhaaldeen", "Bharath", "Bilal", "Bill", "Billy", "Binod", "Bjorn", "Blaike", "Blaine", "Blair", "Blaire", "Blake", "Blazej", "Blazey", "Blessing", "Blue", "Blyth", "Bo", "Boab", "Bob", "Bobby", "Bobby-Lee", "Bodhan", "Boedyn", "Bogdan", "Bohbi", "Bony", "Bowen", "Bowie", "Boyd", "Bracken", "Brad", "Bradan", "Braden", "Bradley", "Bradlie", "Bradly", "Brady", "Bradyn", "Braeden", "Braiden", "Brajan", "Brandan", "Branden", "Brandon", "Brandonlee", "Brandon-Lee", "Brandyn", "Brannan", "Brayden", "Braydon", "Braydyn", "Breandan", "Brehme", "Brendan", "Brendon", "Brendyn", "Breogan", "Bret", "Brett", "Briaddon", "Brian", "Brodi", "Brodie", "Brody", "Brogan", "Broghan", "Brooke", "Brooklin", "Brooklyn", "Bruce", "Bruin", "Bruno", "Brunon", "Bryan", "Bryce", "Bryden", "Brydon", "Brydon-Craig", "Bryn", "Brynmor", "Bryson", "Buddy", "Bully", "Burak", "Burhan", "Butali", "Butchi", "Byron", "Cabhan", "Cadan", "Cade", "Caden", "Cadon", "Cadyn", "Caedan", "Caedyn", "Cael", "Caelan", "Caelen", "Caethan", "Cahl", "Cahlum", "Cai", "Caidan", "Caiden", "Caiden-Paul", "Caidyn", "Caie", "Cailaen", "Cailean", "Caileb-John", "Cailin", "Cain", "Caine", "Cairn", "Cal", "Calan", "Calder", "Cale", "Calean", "Caleb", "Calen", "Caley", "Calib", "Calin", "Callahan", "Callan", "Callan-Adam", "Calley", "Callie", "Callin", "Callum", "Callun", "Callyn", "Calum", "Calum-James", "Calvin", "Cambell", "Camerin", "Cameron", "Campbel", "Campbell", "Camron", "Caolain", "Caolan", "Carl", "Carlo", "Carlos", "Carrich", "Carrick", "Carson", "Carter", "Carwyn", "Casey", "Casper", "Cassy", "Cathal", "Cator", "Cavan", "Cayden", "Cayden-Robert", "Cayden-Tiamo", "Ceejay", "Ceilan", "Ceiran", "Ceirin", "Ceiron", "Cejay", "Celik", "Cephas", "Cesar", "Cesare", "Chad", "Chaitanya", "Chang-Ha", "Charles", "Charley", "Charlie", "Charly", "Chase", "Che", "Chester", "Chevy", "Chi", "Chibudom", "Chidera", "Chimsom", "Chin", "Chintu", "Chiqal", "Chiron", "Chris", "Chris-Daniel", "Chrismedi", "Christian", "Christie", "Christoph", "Christopher", "Christopher-Lee", "Christy", "Chu", "Chukwuemeka", "Cian", "Ciann", "Ciar", "Ciaran", "Ciarian", "Cieran", "Cillian", "Cillin", "Cinar", "CJ", "C-Jay", "Clark", "Clarke", "Clayton", "Clement", "Clifford", "Clyde", "Cobain", "Coban", "Coben", "Cobi", "Cobie", "Coby", "Codey", "Codi", "Codie", "Cody", "Cody-Lee", "Coel", "Cohan", "Cohen", "Colby", "Cole", "Colin", "Coll", "Colm", "Colt", "Colton", "Colum", "Colvin", "Comghan", "Conal", "Conall", "Conan", "Conar", "Conghaile", "Conlan", "Conley", "Conli", "Conlin", "Conlly", "Conlon", "Conlyn", "Connal", "Connall", "Connan", "Connar", "Connel", "Connell", "Conner", "Connolly", "Connor", "Connor-David", "Conor", "Conrad", "Cooper", "Copeland", "Coray", "Corben", "Corbin", "Corey", "Corey-James", "Corey-Jay", "Cori", "Corie", "Corin", "Cormac", "Cormack", "Cormak", "Corran", "Corrie", "Cory", "Cosmo", "Coupar", "Craig", "Craig-James", "Crawford", "Creag", "Crispin", "Cristian", "Crombie", "Cruiz", "Cruz", "Cuillin", "Cullen", "Cullin", "Curtis", "Cyrus", "Daanyaal", "Daegan", "Daegyu", "Dafydd", "Dagon", "Dailey", "Daimhin", "Daithi", "Dakota", "Daksh", "Dale", "Dalong", "Dalton", "Damian", "Damien", "Damon", "Dan", "Danar", "Dane", "Danial", "Daniel", "Daniele", "Daniel-James", "Daniels", "Daniil", "Danish", "Daniyal", "Danniel", "Danny", "Dante", "Danyal", "Danyil", "Danys", "Daood", "Dara", "Darach", "Daragh", "Darcy", "D'arcy", "Dareh", "Daren", "Darien", "Darius", "Darl", "Darn", "Darrach", "Darragh", "Darrel", "Darrell", "Darren", "Darrie", "Darrius", "Darroch", "Darryl", "Darryn", "Darwyn", "Daryl", "Daryn", "Daud", "Daumantas", "Davi", "David", "David-Jay", "David-Lee", "Davie", "Davis", "Davy", "Dawid", "Dawson", "Dawud", "Dayem", "Daymian", "Deacon", "Deagan", "Dean", "Deano", "Decklan", "Declain", "Declan", "Declyan", "Declyn", "Dedeniseoluwa", "Deecan", "Deegan", "Deelan", "Deklain-Jaimes", "Del", "Demetrius", "Denis", "Deniss", "Dennan", "Dennin", "Dennis", "Denny", "Dennys", "Denon", "Denton", "Denver", "Denzel", "Deon", "Derek", "Derick", "Derin", "Dermot", "Derren", "Derrie", "Derrin", "Derron", "Derry", "Derryn", "Deryn", "Deshawn", "Desmond", "Dev", "Devan", "Devin", "Devlin", "Devlyn", "Devon", "Devrin", "Devyn", "Dex", "Dexter", "Dhani", "Dharam", "Dhavid", "Dhyia", "Diarmaid", "Diarmid", "Diarmuid", "Didier", "Diego", "Diesel", "Diesil", "Digby", "Dilan", "Dilano", "Dillan", "Dillon", "Dilraj", "Dimitri", "Dinaras", "Dion", "Dissanayake", "Dmitri", "Doire", "Dolan", "Domanic", "Domenico", "Domhnall", "Dominic", "Dominick", "Dominik", "Donald", "Donnacha", "Donnie", "Dorian", "Dougal", "Douglas", "Dougray", "Drakeo", "Dre", "Dregan", "Drew", "Dugald", "Duncan", "Duriel", "Dustin", "Dylan", "Dylan-Jack", "Dylan-James", "Dylan-John", "Dylan-Patrick", "Dylin", "Dyllan", "Dyllan-James", "Dyllon", "Eadie", "Eagann", "Eamon", "Eamonn", "Eason", "Eassan", "Easton", "Ebow", "Ed", "Eddie", "Eden", "Ediomi", "Edison", "Eduardo", "Eduards", "Edward", "Edwin", "Edwyn", "Eesa", "Efan", "Efe", "Ege", "Ehsan", "Ehsen", "Eiddon", "Eidhan", "Eihli", "Eimantas", "Eisa", "Eli", "Elias", "Elijah", "Eliot", "Elisau", "Eljay", "Eljon", "Elliot", "Elliott", "Ellis", "Ellisandro", "Elshan", "Elvin", "Elyan", "Emanuel", "Emerson", "Emil", "Emile", "Emir", "Emlyn", "Emmanuel", "Emmet", "Eng", "Eniola", "Enis", "Ennis", "Enrico", "Enrique", "Enzo", "Eoghain", "Eoghan", "Eoin", "Eonan", "Erdehan", "Eren", "Erencem", "Eric", "Ericlee", "Erik", "Eriz", "Ernie-Jacks", "Eroni", "Eryk", "Eshan", "Essa", "Esteban", "Ethan", "Etienne", "Etinosa", "Euan", "Eugene", "Evan", "Evann", "Ewan", "Ewen", "Ewing", "Exodi", "Ezekiel", "Ezra", "Fabian", "Fahad", "Faheem", "Faisal", "Faizaan", "Famara", "Fares", "Farhaan", "Farhan", "Farren", "Farzad", "Fauzaan", "Favour", "Fawaz", "Fawkes", "Faysal", "Fearghus", "Feden", "Felix", "Fergal", "Fergie", "Fergus", "Ferre", "Fezaan", "Fiachra", "Fikret", "Filip", "Filippo", "Finan", "Findlay", "Findlay-James", "Findlie", "Finlay", "Finley", "Finn", "Finnan", "Finnean", "Finnen", "Finnlay", "Finnley", "Fintan", "Fionn", "Firaaz", "Fletcher", "Flint", "Florin", "Flyn", "Flynn", "Fodeba", "Folarinwa", "Forbes", "Forgan", "Forrest", "Fox", "Francesco", "Francis", "Francisco", "Franciszek", "Franco", "Frank", "Frankie", "Franklin", "Franko", "Fraser", "Frazer", "Fred", "Freddie", "Frederick", "Fruin", "Fyfe", "Fyn", "Fynlay", "Fynn", "Gabriel", "Gallagher", "Gareth", "Garren", "Garrett", "Garry", "Gary", "Gavin", "Gavin-Lee", "Gene", "Geoff", "Geoffrey", "Geomer", "Geordan", "Geordie", "George", "Georgia", "Georgy", "Gerard", "Ghyll", "Giacomo", "Gian", "Giancarlo", "Gianluca", "Gianmarco", "Gideon", "Gil", "Gio", "Girijan", "Girius", "Gjan", "Glascott", "Glen", "Glenn", "Gordon", "Grady", "Graeme", "Graham", "Grahame", "Grant", "Grayson", "Greg", "Gregor", "Gregory", "Greig", "Griffin", "Griffyn", "Grzegorz", "Guang", "Guerin", "Guillaume", "Gurardass", "Gurdeep", "Gursees", "Gurthar", "Gurveer", "Gurwinder", "Gus", "Gustav", "Guthrie", "Guy", "Gytis", "Habeeb", "Hadji", "Hadyn", "Hagun", "Haiden", "Haider", "Hamad", "Hamid", "Hamish", "Hamza", "Hamzah", "Han", "Hansen", "Hao", "Hareem", "Hari", "Harikrishna", "Haris", "Harish", "Harjeevan", "Harjyot", "Harlee", "Harleigh", "Harley", "Harman", "Harnek", "Harold", "Haroon", "Harper", "Harri", "Harrington", "Harris", "Harrison", "Harry", "Harvey", "Harvie", "Harvinder", "Hasan", "Haseeb", "Hashem", "Hashim", "Hassan", "Hassanali", "Hately", "Havila", "Hayden", "Haydn", "Haydon", "Haydyn", "Hcen", "Hector", "Heddle", "Heidar", "Heini", "Hendri", "Henri", "Henry", "Herbert", "Heyden", "Hiro", "Hirvaansh", "Hishaam", "Hogan", "Honey", "Hong", "Hope", "Hopkin", "Hosea", "Howard", "Howie", "Hristomir", "Hubert", "Hugh", "Hugo", "Humza", "Hunter", "Husnain", "Hussain", "Hussan", "Hussnain", "Hussnan", "Hyden", "I", "Iagan", "Iain", "Ian", "Ibraheem", "Ibrahim", "Idahosa", "Idrees", "Idris", "Iestyn", "Ieuan", "Igor", "Ihtisham", "Ijay", "Ikechukwu", "Ikemsinachukwu", "Ilyaas", "Ilyas", "Iman", "Immanuel", "Inan", "Indy", "Ines", "Innes", "Ioannis", "Ireayomide", "Ireoluwa", "Irvin", "Irvine", "Isa", "Isaa", "Isaac", "Isaiah", "Isak", "Isher", "Ishwar", "Isimeli", "Isira", "Ismaeel", "Ismail", "Israel", "Issiaka", "Ivan", "Ivar", "Izaak", "J", "Jaay", "Jac", "Jace", "Jack", "Jacki", "Jackie", "Jack-James", "Jackson", "Jacky", "Jacob", "Jacques", "Jad", "Jaden", "Jadon", "Jadyn", "Jae", "Jagat", "Jago", "Jaheim", "Jahid", "Jahy", "Jai", "Jaida", "Jaiden", "Jaidyn", "Jaii", "Jaime", "Jai-Rajaram", "Jaise", "Jak", "Jake", "Jakey", "Jakob", "Jaksyn", "Jakub", "Jamaal", "Jamal", "Jameel", "Jameil", "James", "James-Paul", "Jamey", "Jamie", "Jan", "Jaosha", "Jardine", "Jared", "Jarell", "Jarl", "Jarno", "Jarred", "Jarvi", "Jasey-Jay", "Jasim", "Jaskaran", "Jason", "Jasper", "Jaxon", "Jaxson", "Jay", "Jaydan", "Jayden", "Jayden-James", "Jayden-Lee", "Jayden-Paul", "Jayden-Thomas", "Jaydn", "Jaydon", "Jaydyn", "Jayhan", "Jay-Jay", "Jayke", "Jaymie", "Jayse", "Jayson", "Jaz", "Jazeb", "Jazib", "Jazz", "Jean", "Jean-Lewis", "Jean-Pierre", "Jebadiah", "Jed", "Jedd", "Jedidiah", "Jeemie", "Jeevan", "Jeffrey", "Jensen", "Jenson", "Jensyn", "Jeremy", "Jerome", "Jeronimo", "Jerrick", "Jerry", "Jesse", "Jesuseun", "Jeswin", "Jevan", "Jeyun", "Jez", "Jia", "Jian", "Jiao", "Jimmy", "Jincheng", "JJ", "Joaquin", "Joash", "Jock", "Jody", "Joe", "Joeddy", "Joel", "Joey", "Joey-Jack", "Johann", "Johannes", "Johansson", "John", "Johnathan", "Johndean", "Johnjay", "John-Michael", "Johnnie", "Johnny", "Johnpaul", "John-Paul", "John-Scott", "Johnson", "Jole", "Jomuel", "Jon", "Jonah", "Jonatan", "Jonathan", "Jonathon", "Jonny", "Jonothan", "Jon-Paul", "Jonson", "Joojo", "Jordan", "Jordi", "Jordon", "Jordy", "Jordyn", "Jorge", "Joris", "Jorryn", "Josan", "Josef", "Joseph", "Josese", "Josh", "Joshiah", "Joshua", "Josiah", "Joss", "Jostelle", "Joynul", "Juan", "Jubin", "Judah", "Jude", "Jules", "Julian", "Julien", "Jun", "Junior", "Jura", "Justan", "Justin", "Justinas", "Kaan", "Kabeer", "Kabir", "Kacey", "Kacper", "Kade", "Kaden", "Kadin", "Kadyn", "Kaeden", "Kael", "Kaelan", "Kaelin", "Kaelum", "Kai", "Kaid", "Kaidan", "Kaiden", "Kaidinn", "Kaidyn", "Kaileb", "Kailin", "Kain", "Kaine", "Kainin", "Kainui", "Kairn", "Kaison", "Kaiwen", "Kajally", "Kajetan", "Kalani", "Kale", "Kaleb", "Kaleem", "Kal-el", "Kalen", "Kalin", "Kallan", "Kallin", "Kalum", "Kalvin", "Kalvyn", "Kameron", "Kames", "Kamil", "Kamran", "Kamron", "Kane", "Karam", "Karamvir", "Karandeep", "Kareem", "Karim", "Karimas", "Karl", "Karol", "Karson", "Karsyn", "Karthikeya", "Kasey", "Kash", "Kashif", "Kasim", "Kasper", "Kasra", "Kavin", "Kayam", "Kaydan", "Kayden", "Kaydin", "Kaydn", "Kaydyn", "Kaydyne", "Kayleb", "Kaylem", "Kaylum", "Kayne", "Kaywan", "Kealan", "Kealon", "Kean", "Keane", "Kearney", "Keatin", "Keaton", "Keavan", "Keayn", "Kedrick", "Keegan", "Keelan", "Keelin", "Keeman", "Keenan", "Keenan-Lee", "Keeton", "Kehinde", "Keigan", "Keilan", "Keir", "Keiran", "Keiren", "Keiron", "Keiryn", "Keison", "Keith", "Keivlin", "Kelam", "Kelan", "Kellan", "Kellen", "Kelso", "Kelum", "Kelvan", "Kelvin", "Ken", "Kenan", "Kendall", "Kendyn", "Kenlin", "Kenneth", "Kensey", "Kenton", "Kenyon", "Kenzeigh", "Kenzi", "Kenzie", "Kenzo", "Kenzy", "Keo", "Ker", "Kern", "Kerr", "Kevan", "Kevin", "Kevyn", "Kez", "Khai", "Khalan", "Khaleel", "Khaya", "Khevien", "Khizar", "Khizer", "Kia", "Kian", "Kian-James", "Kiaran", "Kiarash", "Kie", "Kiefer", "Kiegan", "Kienan", "Kier", "Kieran", "Kieran-Scott", "Kieren", "Kierin", "Kiern", "Kieron", "Kieryn", "Kile", "Killian", "Kimi", "Kingston", "Kinneil", "Kinnon", "Kinsey", "Kiran", "Kirk", "Kirwin", "Kit", "Kiya", "Kiyonari", "Kjae", "Klein", "Klevis", "Kobe", "Kobi", "Koby", "Koddi", "Koden", "Kodi", "Kodie", "Kody", "Kofi", "Kogan", "Kohen", "Kole", "Konan", "Konar", "Konnor", "Konrad", "Koray", "Korben", "Korbyn", "Korey", "Kori", "Korrin", "Kory", "Koushik", "Kris", "Krish", "Krishan", "Kriss", "Kristian", "Kristin", "Kristofer", "Kristoffer", "Kristopher", "Kruz", "Krzysiek", "Krzysztof", "Ksawery", "Ksawier", "Kuba", "Kurt", "Kurtis", "Kurtis-Jae", "Kyaan", "Kyan", "Kyde", "Kyden", "Kye", "Kyel", "Kyhran", "Kyie", "Kylan", "Kylar", "Kyle", "Kyle-Derek", "Kylian", "Kym", "Kynan", "Kyral", "Kyran", "Kyren", "Kyrillos", "Kyro", "Kyron", "Kyrran", "Lachlainn", "Lachlan", "Lachlann", "Lael", "Lagan", "Laird", "Laison", "Lakshya", "Lance", "Lancelot", "Landon", "Lang", "Lasse", "Latif", "Lauchlan", "Lauchlin", "Laughlan", "Lauren", "Laurence", "Laurie", "Lawlyn", "Lawrence", "Lawrie", "Lawson", "Layne", "Layton", "Lee", "Leigh", "Leigham", "Leighton", "Leilan", "Leiten", "Leithen", "Leland", "Lenin", "Lennan", "Lennen", "Lennex", "Lennon", "Lennox", "Lenny", "Leno", "Lenon", "Lenyn", "Leo", "Leon", "Leonard", "Leonardas", "Leonardo", "Lepeng", "Leroy", "Leven", "Levi", "Levon", "Levy", "Lewie", "Lewin", "Lewis", "Lex", "Leydon", "Leyland", "Leylann", "Leyton", "Liall", "Liam", "Liam-Stephen", "Limo", "Lincoln", "Lincoln-John", "Lincon", "Linden", "Linton", "Lionel", "Lisandro", "Litrell", "Liyonela-Elam", "LLeyton", "Lliam", "Lloyd", "Lloyde", "Loche", "Lochlan", "Lochlann", "Lochlan-Oliver", "Lock", "Lockey", "Logan", "Logann", "Logan-Rhys", "Loghan", "Lokesh", "Loki", "Lomond", "Lorcan", "Lorenz", "Lorenzo", "Lorne", "Loudon", "Loui", "Louie", "Louis", "Loukas", "Lovell", "Luc", "Luca", "Lucais", "Lucas", "Lucca", "Lucian", "Luciano", "Lucien", "Lucus", "Luic", "Luis", "Luk", "Luka", "Lukas", "Lukasz", "Luke", "Lukmaan", "Luqman", "Lyall", "Lyle", "Lyndsay", "Lysander", "Maanav", "Maaz", "Mac", "Macallum", "Macaulay", "Macauley", "Macaully", "Machlan", "Maciej", "Mack", "Mackenzie", "Mackenzy", "Mackie", "Macsen", "Macy", "Madaki", "Maddison", "Maddox", "Madison", "Madison-Jake", "Madox", "Mael", "Magnus", "Mahan", "Mahdi", "Mahmoud", "Maias", "Maison", "Maisum", "Maitlind", "Majid", "Makensie", "Makenzie", "Makin", "Maksim", "Maksymilian", "Malachai", "Malachi", "Malachy", "Malakai", "Malakhy", "Malcolm", "Malik", "Malikye", "Malo", "Ma'moon", "Manas", "Maneet", "Manmohan", "Manolo", "Manson", "Mantej", "Manuel", "Manus", "Marc", "Marc-Anthony", "Marcel", "Marcello", "Marcin", "Marco", "Marcos", "Marcous", "Marcquis", "Marcus", "Mario", "Marios", "Marius", "Mark", "Marko", "Markus", "Marley", "Marlin", "Marlon", "Maros", "Marshall", "Martin", "Marty", "Martyn", "Marvellous", "Marvin", "Marwan", "Maryk", "Marzuq", "Mashhood", "Mason", "Mason-Jay", "Masood", "Masson", "Matas", "Matej", "Mateusz", "Mathew", "Mathias", "Mathu", "Mathuyan", "Mati", "Matt", "Matteo", "Matthew", "Matthew-William", "Matthias", "Max", "Maxim", "Maximilian", "Maximillian", "Maximus", "Maxwell", "Maxx", "Mayeul", "Mayson", "Mazin", "Mcbride", "McCaulley", "McKade", "McKauley", "McKay", "McKenzie", "McLay", "Meftah", "Mehmet", "Mehraz", "Meko", "Melville", "Meshach", "Meyzhward", "Micah", "Michael", "Michael-Alexander", "Michael-James", "Michal", "Michat", "Micheal", "Michee", "Mickey", "Miguel", "Mika", "Mikael", "Mikee", "Mikey", "Mikhail", "Mikolaj", "Miles", "Millar", "Miller", "Milo", "Milos", "Milosz", "Mir", "Mirza", "Mitch", "Mitchel", "Mitchell", "Moad", "Moayd", "Mobeen", "Modoulamin", "Modu", "Mohamad", "Mohamed", "Mohammad", "Mohammad-Bilal", "Mohammed", "Mohanad", "Mohd", "Momin", "Momooreoluwa", "Montague", "Montgomery", "Monty", "Moore", "Moosa", "Moray", "Morgan", "Morgyn", "Morris", "Morton", "Moshy", "Motade", "Moyes", "Msughter", "Mueez", "Muhamadjavad", "Muhammad", "Muhammed", "Muhsin", "Muir", "Munachi", "Muneeb", "Mungo", "Munir", "Munmair", "Munro", "Murdo", "Murray", "Murrough", "Murry", "Musa", "Musse", "Mustafa", "Mustapha", "Muzammil", "Muzzammil", "Mykie", "Myles", "Mylo", "Nabeel", "Nadeem", "Nader", "Nagib", "Naif", "Nairn", "Narvic", "Nash", "Nasser", "Nassir", "Natan", "Nate", "Nathan", "Nathanael", "Nathanial", "Nathaniel", "Nathan-Rae", "Nawfal", "Nayan", "Neco", "Neil", "Nelson", "Neo", "Neshawn", "Nevan", "Nevin", "Ngonidzashe", "Nial", "Niall", "Nicholas", "Nick", "Nickhill", "Nicki", "Nickson", "Nicky", "Nico", "Nicodemus", "Nicol", "Nicolae", "Nicolas", "Nidhish", "Nihaal", "Nihal", "Nikash", "Nikhil", "Niki", "Nikita", "Nikodem", "Nikolai", "Nikos", "Nilav", "Niraj", "Niro", "Niven", "Noah", "Noel", "Nolan", "Noor", "Norman", "Norrie", "Nuada", "Nyah", "Oakley", "Oban", "Obieluem", "Obosa", "Odhran", "Odin", "Odynn", "Ogheneochuko", "Ogheneruno", "Ohran", "Oilibhear", "Oisin", "Ojima-Ojo", "Okeoghene", "Olaf", "Ola-Oluwa", "Olaoluwapolorimi", "Ole", "Olie", "Oliver", "Olivier", "Oliwier", "Ollie", "Olurotimi", "Oluwadamilare", "Oluwadamiloju", "Oluwafemi", "Oluwafikunayomi", "Oluwalayomi", "Oluwatobiloba", "Oluwatoni", "Omar", "Omri", "Oran", "Orin", "Orlando", "Orley", "Orran", "Orrick", "Orrin", "Orson", "Oryn", "Oscar", "Osesenagha", "Oskar", "Ossian", "Oswald", "Otto", "Owain", "Owais", "Owen", "Owyn", "Oz", "Ozzy", "Pablo", "Pacey", "Padraig", "Paolo", "Pardeepraj", "Parkash", "Parker", "Pascoe", "Pasquale", "Patrick", "Patrick-John", "Patrikas", "Patryk", "Paul", "Pavit", "Pawel", "Pawlo", "Pearce", "Pearse", "Pearsen", "Pedram", "Pedro", "Peirce", "Peiyan", "Pele", "Peni", "Peregrine", "Peter", "Phani", "Philip", "Philippos", "Phinehas", "Phoenix", "Phoevos", "Pierce", "Pierre-Antoine", "Pieter", "Pietro", "Piotr", "Porter", "Prabhjoit", "Prabodhan", "Praise", "Pranav", "Pravin", "Precious", "Prentice", "Presley", "Preston", "Preston-Jay", "Prinay", "Prince", "Prithvi", "Promise", "Puneetpaul", "Pushkar", "Qasim", "Qirui", "Quinlan", "Quinn", "Radmiras", "Raees", "Raegan", "Rafael", "Rafal", "Rafferty", "Rafi", "Raheem", "Rahil", "Rahim", "Rahman", "Raith", "Raithin", "Raja", "Rajab-Ali", "Rajan", "Ralfs", "Ralph", "Ramanas", "Ramit", "Ramone", "Ramsay", "Ramsey", "Rana", "Ranolph", "Raphael", "Rasmus", "Rasul", "Raul", "Raunaq", "Ravin", "Ray", "Rayaan", "Rayan", "Rayane", "Rayden", "Rayhan", "Raymond", "Rayne", "Rayyan", "Raza", "Reace", "Reagan", "Reean", "Reece", "Reed", "Reegan", "Rees", "Reese", "Reeve", "Regan", "Regean", "Reggie", "Rehaan", "Rehan", "Reice", "Reid", "Reigan", "Reilly", "Reily", "Reis", "Reiss", "Remigiusz", "Remo", "Remy", "Ren", "Renars", "Reng", "Rennie", "Reno", "Reo", "Reuben", "Rexford", "Reynold", "Rhein", "Rheo", "Rhett", "Rheyden", "Rhian", "Rhoan", "Rholmark", "Rhoridh", "Rhuairidh", "Rhuan", "Rhuaridh", "Rhudi", "Rhy", "Rhyan", "Rhyley", "Rhyon", "Rhys", "Rhys-Bernard", "Rhyse", "Riach", "Rian", "Ricards", "Riccardo", "Ricco", "Rice", "Richard", "Richey", "Richie", "Ricky", "Rico", "Ridley", "Ridwan", "Rihab", "Rihan", "Rihards", "Rihonn", "Rikki", "Riley", "Rio", "Rioden", "Rishi", "Ritchie", "Rivan", "Riyadh", "Riyaj", "Roan", "Roark", "Roary", "Rob", "Robbi", "Robbie", "Robbie-lee", "Robby", "Robert", "Robert-Gordon", "Robertjohn", "Robi", "Robin", "Rocco", "Roddy", "Roderick", "Rodrigo", "Roen", "Rogan", "Roger", "Rohaan", "Rohan", "Rohin", "Rohit", "Rokas", "Roman", "Ronald", "Ronan", "Ronan-Benedict", "Ronin", "Ronnie", "Rooke", "Roray", "Rori", "Rorie", "Rory", "Roshan", "Ross", "Ross-Andrew", "Rossi", "Rowan", "Rowen", "Roy", "Ruadhan", "Ruaidhri", "Ruairi", "Ruairidh", "Ruan", "Ruaraidh", "Ruari", "Ruaridh", "Ruben", "Rubhan", "Rubin", "Rubyn", "Rudi", "Rudy", "Rufus", "Rui", "Ruo", "Rupert", "Ruslan", "Russel", "Russell", "Ryaan", "Ryan", "Ryan-Lee", "Ryden", "Ryder", "Ryese", "Ryhs", "Rylan", "Rylay", "Rylee", "Ryleigh", "Ryley", "Rylie", "Ryo", "Ryszard", "Saad", "Sabeen", "Sachkirat", "Saffi", "Saghun", "Sahaib", "Sahbian", "Sahil", "Saif", "Saifaddine", "Saim", "Sajid", "Sajjad", "Salahudin", "Salman", "Salter", "Salvador", "Sam", "Saman", "Samar", "Samarjit", "Samatar", "Sambrid", "Sameer", "Sami", "Samir", "Sami-Ullah", "Samual", "Samuel", "Samuela", "Samy", "Sanaullah", "Sandro", "Sandy", "Sanfur", "Sanjay", "Santiago", "Santino", "Satveer", "Saul", "Saunders", "Savin", "Sayad", "Sayeed", "Sayf", "Scot", "Scott", "Scott-Alexander", "Seaan", "Seamas", "Seamus", "Sean", "Seane", "Sean-James", "Sean-Paul", "Sean-Ray", "Seb", "Sebastian", "Sebastien", "Selasi", "Seonaidh", "Sephiroth", "Sergei", "Sergio", "Seth", "Sethu", "Seumas", "Shaarvin", "Shadow", "Shae", "Shahmir", "Shai", "Shane", "Shannon", "Sharland", "Sharoz", "Shaughn", "Shaun", "Shaunpaul", "Shaun-Paul", "Shaun-Thomas", "Shaurya", "Shaw", "Shawn", "Shawnpaul", "Shay", "Shayaan", "Shayan", "Shaye", "Shayne", "Shazil", "Shea", "Sheafan", "Sheigh", "Shenuk", "Sher", "Shergo", "Sheriff", "Sherwyn", "Shiloh", "Shiraz", "Shreeram", "Shreyas", "Shyam", "Siddhant", "Siddharth", "Sidharth", "Sidney", "Siergiej", "Silas", "Simon", "Sinai", "Skye", "Sofian", "Sohaib", "Sohail", "Soham", "Sohan", "Sol", "Solomon", "Sonneey", "Sonni", "Sonny", "Sorley", "Soul", "Spencer", "Spondon", "Stanislaw", "Stanley", "Stefan", "Stefano", "Stefin", "Stephen", "Stephenjunior", "Steve", "Steven", "Steven-lee", "Stevie", "Stewart", "Stewarty", "Strachan", "Struan", "Stuart", "Su", "Subhaan", "Sudais", "Suheyb", "Suilven", "Sukhi", "Sukhpal", "Sukhvir", "Sulayman", "Sullivan", "Sultan", "Sung", "Sunny", "Suraj", "Surien", "Sweyn", "Syed", "Sylvain", "Symon", "Szymon", "Tadd", "Taddy", "Tadhg", "Taegan", "Taegen", "Tai", "Tait", "Taiwo", "Talha", "Taliesin", "Talon", "Talorcan", "Tamar", "Tamiem", "Tammam", "Tanay", "Tane", "Tanner", "Tanvir", "Tanzeel", "Taonga", "Tarik", "Tariq-Jay", "Tate", "Taylan", "Taylar", "Tayler", "Taylor", "Taylor-Jay", "Taylor-Lee", "Tayo", "Tayyab", "Tayye", "Tayyib", "Teagan", "Tee", "Teejay", "Tee-jay", "Tegan", "Teighen", "Teiyib", "Te-Jay", "Temba", "Teo", "Teodor", "Teos", "Terry", "Teydren", "Theo", "Theodore", "Thiago", "Thierry", "Thom", "Thomas", "Thomas-Jay", "Thomson", "Thorben", "Thorfinn", "Thrinei", "Thumbiko", "Tiago", "Tian", "Tiarnan", "Tibet", "Tieran", "Tiernan", "Timothy", "Timucin", "Tiree", "Tisloh", "Titi", "Titus", "Tiylar", "TJ", "Tjay", "T-Jay", "Tobey", "Tobi", "Tobias", "Tobie", "Toby", "Todd", "Tokinaga", "Toluwalase", "Tom", "Tomas", "Tomasz", "Tommi-Lee", "Tommy", "Tomson", "Tony", "Torin", "Torquil", "Torran", "Torrin", "Torsten", "Trafford", "Trai", "Travis", "Tre", "Trent", "Trey", "Tristain", "Tristan", "Troy", "Tubagus", "Turki", "Turner", "Ty", "Ty-Alexander", "Tye", "Tyelor", "Tylar", "Tyler", "Tyler-James", "Tyler-Jay", "Tyllor", "Tylor", "Tymom", "Tymon", "Tymoteusz", "Tyra", "Tyree", "Tyrnan", "Tyrone", "Tyson", "Ubaid", "Ubayd", "Uchenna", "Uilleam", "Umair", "Umar", "Umer", "Umut", "Urban", "Uri", "Usman", "Uzair", "Uzayr", "Valen", "Valentin", "Valentino", "Valery", "Valo", "Vasyl", "Vedantsinh", "Veeran", "Victor", "Victory", "Vinay", "Vince", "Vincent", "Vincenzo", "Vinh", "Vinnie", "Vithujan", "Vladimir", "Vladislav", "Vrishin", "Vuyolwethu", "Wabuya", "Wai", "Walid", "Wallace", "Walter", "Waqaas", "Warkhas", "Warren", "Warrick", "Wasif", "Wayde", "Wayne", "Wei", "Wen", "Wesley", "Wesley-Scott", "Wiktor", "Wilkie", "Will", "William", "William-John", "Willum", "Wilson", "Windsor", "Wojciech", "Woyenbrakemi", "Wyatt", "Wylie", "Wynn", "Xabier", "Xander", "Xavier", "Xiao", "Xida", "Xin", "Xue", "Yadgor", "Yago", "Yahya", "Yakup", "Yang", "Yanick", "Yann", "Yannick", "Yaseen", "Yasin", "Yasir", "Yassin", "Yoji", "Yong", "Yoolgeun", "Yorgos", "Youcef", "Yousif", "Youssef", "Yu", "Yuanyu", "Yuri", "Yusef", "Yusuf", "Yves", "Zaaine", "Zaak", "Zac", "Zach", "Zachariah", "Zacharias", "Zacharie", "Zacharius", "Zachariya", "Zachary", "Zachary-Marc", "Zachery", "Zack", "Zackary", "Zaid", "Zain", "Zaine", "Zaineddine", "Zainedin", "Zak", "Zakaria", "Zakariya", "Zakary", "Zaki", "Zakir", "Zakk", "Zamaar", "Zander", "Zane", "Zarran", "Zayd", "Zayn", "Zayne", "Ze", "Zechariah", "Zeek", "Zeeshan", "Zeid", "Zein", "Zen", "Zendel", "Zenith", "Zennon", "Zeph", "Zerah", "Zhen", "Zhi", "Zhong", "Zhuo", "Zi", "Zidane", "Zijie", "Zinedine", "Zion", "Zishan", "Ziya", "Ziyaan", "Zohaib", "Zohair", "Zoubaeir", "Zubair", "Zubayr", "Zuriel"};

    public static String generateRandomName() {

        return LAST_NAMES[RANDOM.nextInt(LAST_NAMES.length)];
    }

    public static void selectByOptions(WebElement selectElement, String optionToSelect, WebDriver driver) {

        try {

            waitForElementToBeDisplayed(selectElement, 10);
            Cls_Generic_Methods.clickElementByJS(driver, selectElement);
            List<WebElement> allOptions = selectElement.findElements(By.xpath("./option"));
            for (WebElement option : allOptions) {
                String optionValue = option.getText();
                if (optionValue.contains((optionToSelect))) {
                    option.click();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean selectStoreOnApp(String nameOfStoreToSelect, WebDriver driver) {
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        boolean tabIsSelected = false;

        try {
            //Checking whether dropdown is already clicked
            if (!waitForElementToBeDisplayed(oPage_Navbar.input_searchStore, 1)) {
                Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_storesSelector);
            }

            Cls_Generic_Methods.clearValuesInElement(oPage_Navbar.input_searchStore);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Navbar.input_searchStore, nameOfStoreToSelect);
            Cls_Generic_Methods.customWait(2);

            for (WebElement eStore : oPage_Navbar.updated_list_storesSelector) {

                String currentStoreName = eStore.getText();

                if (currentStoreName.equals(nameOfStoreToSelect)) {
                    eStore.click();
                    tabIsSelected = true;
                    Cls_Generic_Methods.waitForPageLoad(driver, 20);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tabIsSelected;
    }

    public static boolean selectOptionFromLeftInInventoryStorePanel(String sInputParentOption, String sInputChildOption, WebDriver driver) {
        boolean operationSuccess = false;
        boolean parentOptionSelected = false;
        Page_Store oPage_Store = new Page_Store(driver);


        try {

            for (WebElement parentElement : oPage_Store.list_ParentOptionsOnLeft) {
                String sParentOptionTextOnUI = parentElement.getText();
                sParentOptionTextOnUI = sParentOptionTextOnUI.split("\\n")[0];

                System.out.println(sParentOptionTextOnUI);
                int requiredIndex = oPage_Store.list_ParentOptionsOnLeft.indexOf(parentElement);

                if (sParentOptionTextOnUI.equalsIgnoreCase(sInputParentOption)) {
                    if (requiredIndex != 0) {
                        Cls_Generic_Methods.clickElementByJS(driver, oPage_Store.list_namesParentOptionsOnLeft.get(requiredIndex));
                        Cls_Generic_Methods.customWait(1);
                    }
                    parentOptionSelected = true;

                    for (WebElement childElement : oPage_Store.list_ChildOptionsOnLeft) {

                        if (childElement.isDisplayed()) {
                            String sChildOptionTextOnUI = childElement.getText();

                            System.out.println(sChildOptionTextOnUI);
                            System.out.println(oPage_Store.list_ChildOptionsOnLeft.indexOf(childElement));

                            if (sChildOptionTextOnUI.equalsIgnoreCase(sInputChildOption)) {
                                Cls_Generic_Methods.customWait();
                                Cls_Generic_Methods.clickElementByAction(driver, childElement);
                                Cls_Generic_Methods.customWait(4);
                                operationSuccess = true;
                                break;
                            }
                        }
                    }
                }

                if (parentOptionSelected) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return operationSuccess;
    }

    public static boolean switchToOtherTab(WebDriver driver) {

        Set<String> windowHandlesSet = driver.getWindowHandles();
        String sCurrentWindowHandle = driver.getWindowHandle();
        boolean bSwitchToOtherTabSuccess = false;

        try {

            for (String sWindowHandle : windowHandlesSet) {
                if (!sCurrentWindowHandle.equals(sWindowHandle)) {
                    driver.switchTo().window(sWindowHandle);
                    bSwitchToOtherTabSuccess = true;
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bSwitchToOtherTabSuccess;
    }

    public static boolean waitForElementToBeDisplayed(WebElement element, int maxSecondTimeoutInSecs,
                                                      boolean... isFailOnException) throws Exception {

        boolean flag = false;
        logger.info("INTO waitForElementToBeDisplayed method for " + element + " with timeout = " + maxSecondTimeoutInSecs);
        try {
            for (int i = 0; i < maxSecondTimeoutInSecs; i++) {
                try {
                    Thread.sleep(1000);
                    if (element.isDisplayed()) {
                        logger.info("Element is displayed in " + i + " secs");
                        flag = true;
                        break;
                    }

                } catch (Exception e) {

                }
            }
            if (!flag) {
                logger.error("Element is not display within " + maxSecondTimeoutInSecs + "Sec.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal("Error while waiting for element to be displayed. <br>\n" + e);
        }
        return flag;
    }

    public static boolean selectDepartmentOnApp(WebDriver driver, String nameOfDeptToSelect) {
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        boolean tabIsSelected = false;

        try {
            Cls_Generic_Methods.clickElementByJS(driver, oPage_Navbar.button_departmentFromDropdownSelector);

            for (WebElement eDepartment : oPage_Navbar.list_departmentSelector) {
                String currentDeptName = eDepartment.getText().trim();

                if (currentDeptName.equalsIgnoreCase(nameOfDeptToSelect)) {
                    eDepartment.click();
                    tabIsSelected = true;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);

            try {
                for (WebElement eDepartment : oPage_Navbar.list_departmentsInOneLine) {
                    String currentDeptName = eDepartment.getText();
                    if (currentDeptName.equalsIgnoreCase(nameOfDeptToSelect)) {
                        eDepartment.click();
                        tabIsSelected = true;
                        break;
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                m_assert.assertFatal(" " + e1);
            }
        }
        return tabIsSelected;
    }

    public static boolean selectOptionUnderSettings(WebDriver driver, String inputOptionName) {
        boolean operationSuccess = false;
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_Settings oPage_Settings = new Page_Settings(driver);

        try {
            waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);

            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
            waitForElementToBeDisplayed(oPage_Navbar.text_currentUser, 5);

            for (WebElement eOption : oPage_Navbar.list_OptionsUnderSettingsAndLogout) {
                String sOptionText = eOption.getText();

                if (sOptionText.equalsIgnoreCase(inputOptionName)) {
                    Cls_Generic_Methods.clickElement(eOption);
                    operationSuccess = true;
                    break;
                }
            }

        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }

        return operationSuccess;
    }

    public static boolean selectOptionFromLeftInSettingsPanel(WebDriver driver, String sInputParentOption, String sInputChildOption) {
        boolean operationSuccess = false;
        boolean parentOptionSelected = false;
        Page_Settings oPage_Settings = new Page_Settings(driver);

        try {

            for (WebElement parentElement : oPage_Settings.list_ParentOptionsOnLeft) {
                String sParentOptionTextOnUI = parentElement.getText().trim();
                sParentOptionTextOnUI = sParentOptionTextOnUI.split("\\n")[0];

                System.out.println(sParentOptionTextOnUI);
                int requiredIndex = oPage_Settings.list_ParentOptionsOnLeft.indexOf(parentElement);

                if (sParentOptionTextOnUI.equalsIgnoreCase(sInputParentOption)) {
                    if (requiredIndex != 0) {
                        Cls_Generic_Methods.clickElementByJS(driver, oPage_Settings.list_ArrowBesideParentOptionsOnLeft.get(requiredIndex));
                        Cls_Generic_Methods.customWait(1);
                    }
                    parentOptionSelected = true;

                    for (WebElement childElement : oPage_Settings.list_ChildOptionsOnLeft) {

                        if (childElement.isDisplayed()) {
                            String sChildOptionTextOnUI = childElement.getText().trim();

                            System.out.println(sChildOptionTextOnUI);
                            System.out.println(oPage_Settings.list_ChildOptionsOnLeft.indexOf(childElement));

                            if (sChildOptionTextOnUI.equalsIgnoreCase(sInputChildOption)) {
                                Cls_Generic_Methods.clickElementByAction(driver, childElement);
                                Cls_Generic_Methods.customWait(4);
                                operationSuccess = true;
                                break;
                            }
                        }
                    }
                }

                if (parentOptionSelected) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return operationSuccess;
    }

    public static void launchDriverInInstalledBrowser() throws Exception {
        String currentUserChromeUserDataPath_Win = "", userHomeDir = "";
        userHomeDir = System.getProperty("user.home");

        try {
            logger.info("Into launchDriverInInstalledBrowser for Windows System");

            Thread.sleep(1000);

            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator
                    + "Resources" + File.separator + "Drivers" + File.separator + "chromedriver.exe");

            currentUserChromeUserDataPath_Win = userHomeDir + File.separator + "AppData" + File.separator
                    + "Local" + File.separator + "Google" + File.separator + "Chrome" + File.separator + "User Data";


//			https://stackoverflow.com/questions/75678572/java-io-ioexception-invalid-status-code-403-text-forbidden
            ChromeOptions options = new ChromeOptions();
            options.addArguments("user-data-dir=" + currentUserChromeUserDataPath_Win);
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("start-maximized");
            options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
            sDriver = new ChromeDriver(options);

            url = Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase());
            sDriver.navigate().to(url);

        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal("Browser not launched.\n" + e);
            throw new Exception("Browser not launched.");
        }

    }

    // getModuleSequence().get(0) = Primary Sequence

    static ArrayList<String> beforeSequenceList;
    static boolean beforeSequence = false;

    public static void validateSequenceManager(SoftAssert m_assert, String sModule, String sDepartment) {
        WebDriver driver = sDriver;

        try {
            Page_SequenceManager oPage_SequenceManager = new Page_SequenceManager(driver);

            login(m_assert, driver, user);
            driver.navigate().refresh();
            Cls_Generic_Methods.customWait();

            selectOptionUnderSettings(driver, Settings_Data.option_ORGANISATION_SETTING);
            Cls_Generic_Methods.customWait(5);
            selectOptionFromLeftInSettingsPanel(driver, "General", "Sequence Manager");
            waitForElementToBeDisplayed(oPage_SequenceManager.header_manageSequenceHeader, 20);

            if (!beforeSequence) {
                beforeSequenceList = getModuleSequence(driver, sModule, sDepartment);
                beforeSequence = true;
            } else {

                ArrayList<String> afterSequenceList = getModuleSequence(driver, sModule, sDepartment);

                for (int noOfCounterLevel = 0; noOfCounterLevel < afterSequenceList.size(); noOfCounterLevel++) {

                    String beforeCounterValue = beforeSequenceList.get(noOfCounterLevel).split(":")[1];

                    String afterCounterLevel = afterSequenceList.get(noOfCounterLevel).split(":")[0];
                    String afterCounterValue = afterSequenceList.get(noOfCounterLevel).split(":")[1];

                    //To get counter value
                    int beforeCounterNumber = getCounterNumber(beforeCounterValue);
                    int afterCounterNumber = getCounterNumber(afterCounterValue);

                    int expectedCounterNumber = beforeCounterNumber + noOfTabs;

                    //Skip entity counter(Unable to Validate)
                    if (afterCounterLevel.equalsIgnoreCase("Entity Group")) {
                        continue;
                    }

                    //Primary
                    if (noOfCounterLevel == 0) {
                        m_assert.assertTrue(afterCounterNumber == expectedCounterNumber, "Validated Primary Sequence counter got increased by " + noOfTabs + " count");
                        m_assert.assertInfo("Counter Level : " + afterCounterLevel + " = BEFORE : " + " --> <font color='yellow'>" + beforeCounterValue + "</font>" + " --> AFTER : " + " --> <font color='lime'>" + afterCounterValue + "</font>");

                    } else {
                        m_assert.assertTrue(afterCounterNumber == expectedCounterNumber, "Validated Secondary Sequence counter got increased by " + noOfTabs + " count");
                        m_assert.assertInfo("Counter Level : " + afterCounterLevel + " = BEFORE : " + " --> <font color='yellow'>" + beforeCounterValue + "</font>" + " --> AFTER : " + " --> <font color='lime'>" + afterCounterValue + "</font>");

                    }

                }
                beforeSequence = false;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static boolean runOnce = true;

    public static ArrayList<String> getModuleSequence(WebDriver driver, String sModule, String sDepartment) {
        Page_SequenceManager oPage_SequenceManager = new Page_SequenceManager(driver);
        ArrayList<String> sequenceList = new ArrayList<>();

        try {

            boolean moduleFound = false;
            boolean nextModule = false;

            for (int i = 0; i < oPage_SequenceManager.list_sequenceTableRawDataList.size(); i++) {
                String fullPropertiesText = oPage_SequenceManager.list_sequenceTableRawDataList.get(i).getText().trim();
                System.out.println("----------> " + fullPropertiesText);

                if (fullPropertiesText.contains(sModule) || moduleFound) {
                    if (!fullPropertiesText.contains(sDepartment)) {
                        moduleFound = true;
                        continue;
                    }
                    if (nextModule) {
                        break;
                    }
                } else {
                    continue;
                }

                String sPrimarySequence = (oPage_SequenceManager.list_sequenceTableRawDataList.get(i).findElements(By.xpath("./td")).get(3)).getText().trim();
                String sPrimaryCounterLevel = (oPage_SequenceManager.list_sequenceTableRawDataList.get(i).findElement(By.xpath(".//*[@class='counterLevel']"))).getText().trim();
                if (runOnce) {
                    Cls_Generic_Methods.clickElement(driver, oPage_SequenceManager.list_sequenceTableRawDataList.get(i).findElement(By.xpath(".//a[@title='Edit Sequence']")));
                    waitForElementToBeDisplayed(oPage_SequenceManager.input_counterCodeAbbreviation, 10);

                    //Changing format
                    if (!oPage_SequenceManager.text_sequenceFormat.get(oPage_SequenceManager.text_sequenceFormat.size() - 1).getText().contains("Counter")) {
                        Cls_Generic_Methods.clickElement(oPage_SequenceManager.input_counterCodeAbbreviation.findElement(By.xpath("./ancestor::div[contains(@class,'counter_rows')]//button[contains(@class,'remove_entity')]")));
                        Cls_Generic_Methods.customWait();

                        if (!oPage_SequenceManager.text_sequenceFormat.get(oPage_SequenceManager.text_sequenceFormat.size() - 1).getText().equalsIgnoreCase("Separator")) {
                            Cls_Generic_Methods.selectElementByVisibleText(oPage_SequenceManager.select_addSeparators, "/");
                            Cls_Generic_Methods.customWait();
                        }

                        Cls_Generic_Methods.clickElement(oPage_SequenceManager.button_counterAddSequence);
                        Cls_Generic_Methods.customWait();
                    }
                    Cls_Generic_Methods.clickElement(oPage_SequenceManager.button_updateEditSequence);
                    Cls_Generic_Methods.customWait(5);
                }

                sequenceList.add(sPrimaryCounterLevel + ":" + sPrimarySequence);

                List<WebElement> sequenceRow = oPage_SequenceManager.list_sequenceTableRawDataList;
                while (true) {
                    i++;
                    String sNoOfPropertiesCheck = oPage_SequenceManager.list_sequenceTableRawDataList.get(i).findElement(By.xpath("./td[3]")).getText();
                    if (sNoOfPropertiesCheck.isEmpty()) {
                        String sSecondarySequence = (sequenceRow.get(i).findElements(By.xpath("./td")).get(3)).getText().trim();
                        String sSecondaryCounterLevel = sequenceRow.get(i).findElement(By.xpath(".//span[@class='counterLevel']")).getText().trim();

                        if (runOnce) {
                            Cls_Generic_Methods.clickElementByJS(driver, sequenceRow.get(i).findElement(By.xpath(".//a[@title='Edit Sequence']")));
                            waitForElementToBeDisplayed(oPage_SequenceManager.input_counterCodeAbbreviation, 10);

                            //Changing Format
                            if (!oPage_SequenceManager.text_sequenceFormat.get(oPage_SequenceManager.text_sequenceFormat.size() - 1).getText().contains("Counter")) {
                                Cls_Generic_Methods.clickElement(oPage_SequenceManager.input_counterCodeAbbreviation.findElement(By.xpath("./ancestor::div[contains(@class,'counter_rows')]//button[contains(@class,'remove_entity')]")));
                                Cls_Generic_Methods.customWait();

                                if (!oPage_SequenceManager.text_sequenceFormat.get(oPage_SequenceManager.text_sequenceFormat.size() - 1).getText().equalsIgnoreCase("Separator")) {
                                    Cls_Generic_Methods.selectElementByVisibleText(oPage_SequenceManager.select_addSeparators, "/");
                                    Cls_Generic_Methods.customWait();
                                }

                                Cls_Generic_Methods.clickElement(oPage_SequenceManager.button_counterAddSequence);
                                Cls_Generic_Methods.customWait();
                            }
                            Cls_Generic_Methods.clickElement(oPage_SequenceManager.button_updateEditSequence);
                            Cls_Generic_Methods.customWait(5);
                            runOnce = false;
                        }

                        sequenceList.add(sSecondaryCounterLevel + ":" + sSecondarySequence);
                    } else {
                        nextModule = true;
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sequenceList;
    }

    public static int getCounterNumber(String counterValue) {

        int lastIndex = Math.max(counterValue.lastIndexOf('/'), counterValue.lastIndexOf('-'));
        String aCounter = counterValue.substring(lastIndex + 1);

        return Integer.parseInt(aCounter);
    }


}

