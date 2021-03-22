package edu.sjsu.android.test_hw2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

/**
 * MAIN ACTIVITY CLASS THAT HOLDS THE MAIN LAYOUT
 * IT HOLDS AND INITIALIZES THE LIST OF ANIMALS,
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView view;
    private RecyclerView.Adapter customAdapter;
    public ArrayList<Animal> animalList;

    @Override
    /**
     * overridden method to create and inflate the action menu items
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    /**
     * overridden method that defines the functions of options on the action bar
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_information:
                Intent intent = new Intent(view.getContext(), Information.class);
                view.getContext().startActivity(intent);
                return (true);
            case R.id.action_uninstall:
                Uri packageUri = Uri.parse("package:edu.sjsu.android.test_hw2");
                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageUri);
                startActivity(uninstallIntent);
                return (true);

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    /**
     * creates the main view and sets adapters for the recycler view
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animalList = new ArrayList<>();
        initList(animalList);

        view = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        view.setLayoutManager(layoutManager);

        customAdapter = new CustomAdapter(animalList);
        view.setAdapter(customAdapter);

    }


    /**
     * private method that initializes the list of animals to be included on the app
     * @param animalList the list to be initialized
     */
    private void initList(ArrayList<Animal> animalList) {
        animalList.add(new Animal("Lion",  R.drawable.lion, "The lion (Panthera leo) is a species in the family Felidae and a member of the genus Panthera. It has a muscular, deep-chested body, short, rounded head, round ears, and a hairy tuft at the end of its tail. It is sexually dimorphic; adult male lions have a prominent mane. With a typical head-to-body length of 184–208 cm (72–82 in) they are larger than females at 160–184 cm (63–72 in). It is a social species, forming groups called prides. A lion pride consists of a few adult males, related females and cubs. Groups of female lions usually hunt together, preying mostly on large ungulates. The lion is an apex and keystone predator; although some lions scavenge when opportunities occur and have been known to hunt humans, the species typically does not."));
        animalList.add(new Animal("Bear",  R.drawable.bear, "Bears are carnivoran mammals of the family Ursidae. They are classified as caniforms, or doglike carnivorans. Although only eight species of bears are extant, they are widespread, appearing in a wide variety of habitats throughout the Northern Hemisphere and partially in the Southern Hemisphere. Bears are found on the continents of North America, South America, Europe, and Asia. Common characteristics of modern bears include large bodies with stocky legs, long snouts, small rounded ears, shaggy hair, plant grade paws with five non retractile claws, and short tails. "));
        animalList.add(new Animal("Cheetah",  R.drawable.cheetah, "The cheetah (Acinonyx jubatus) is a large cat native to Africa and central Iran. It is the fastest land animal, capable of running at 80 to 128 km/h (50 to 80 mph), and as such has several adaptations for speed, including a light build, long thin legs and a long tail. Cheetahs typically reach 67–94 cm (26–37 in) at the shoulder, and the head-and-body length is between 1.1 and 1.5 m (3.6 and 4.9 ft). Adults typically weigh between 20 and 65 kg (44 and 143 lb). Its head is small, rounded, and has a short snout and black tear-like facial streaks. The coat is typically tawny to creamy white or pale buff and is mostly covered with evenly spaced, solid black spots. Four subspecies are recognised."));
        animalList.add(new Animal("Elephant",  R.drawable.elephant, "Elephants are mammals of the family Elephantidae and the largest existing land animals. Three species are currently recognised: the African bush elephant, the African forest elephant, and the Asian elephant. Elephantidae is the only surviving family of the order Proboscidea; extinct members include the mastodons. The family Elephantidae also contains several now-extinct groups, including the mammoths and straight-tusked elephants. African elephants have larger ears and concave backs, whereas Asian elephants have smaller ears, and convex or level backs. Distinctive features of all elephants include a long trunk, tusks, large ear flaps, massive legs, and tough but sensitive skin. The trunk, also called a proboscis, is used for breathing, bringing food and water to the mouth, and grasping objects. Tusks, which are derived from the incisor teeth, serve both as weapons and as tools for moving objects and digging. The large ear flaps assist in maintaining a constant body temperature as well as in communication. The pillar-like legs carry their great weight."));
        animalList.add(new Animal("Panda",  R.drawable.panda, "The giant panda (Ailuropoda melanoleuca; Chinese: 大熊猫; pinyin: dàxióngmāo), also known as the panda bear or simply the panda, is a bear[6] native to south central China.It is characterised by large, black patches around its eyes, over the ears, and across its round body. The name \"giant panda\" is sometimes used to distinguish it from the red panda, a neighboring musteloid. Though it belongs to the order Carnivora, the giant panda is a folivore, with bamboo shoots and leaves making up more than 99% of its diet. Giant pandas in the wild will occasionally eat other grasses, wild tubers, or even meat in the form of birds, rodents, or carrion. In captivity, they may receive honey, eggs, fish, yams, shrub leaves, oranges, or bananas along with specially prepared food."));
        animalList.add(new Animal("Wolf",  R.drawable.wolf, "The wolf (Canis lupus), also known as the gray wolf or grey wolf, is a large canine native to Eurasia and North America. More than thirty subspecies of Canis lupus have been recognized, and gray wolves, as colloquially understood, comprise non-domestic/feral subspecies. The wolf is the largest extant member of Canidae, males averaging 40 kg (88 lb) and females 37 kg (82 lb). Wolves measure 105–160 cm (41–63 in) in length and 80–85 cm (31–33 in) at shoulder height. The wolf is also distinguished from other Canis species by its less pointed ears and muzzle, as well as a shorter torso and a longer tail. The wolf is nonetheless related closely enough to smaller Canis species, such as the coyote and the golden jackal, to produce fertile hybrids with them. The banded fur of a wolf is usually mottled white, brown, gray, and black, although subspecies in the arctic region may be nearly all white."));
        animalList.add(new Animal("Giraffe",  R.drawable.giraffe, "The giraffe (Giraffa) is an African artiodactyl mammal, the tallest living terrestrial animal and the largest ruminant. It is traditionally considered to be one species, Giraffa camelopardalis, with nine subspecies. However, the existence of up to eight extant giraffe species has been described, based upon research into the mitochondrial and nuclear DNA, as well as morphological measurements of Giraffa. Seven other species are extinct, prehistoric species known from fossils. "));
        animalList.add(new Animal("Gorilla",  R.drawable.gorila, " Gorillas are ground-dwelling, predominantly herbivorous apes that inhabit the forest of central Sub-Saharan Africa. The genus Gorilla is divided into two species: the eastern gorillas and the western gorillas (both critically endangered), and either four or five subspecies. They are the largest living primates. The DNA of gorillas is highly similar to that of humans, from 95 to 99% depending on what is included, and they are the next closest living relatives to humans after the chimpanzees and bonobos."));
        animalList.add(new Animal("Tiger",  R.drawable.tiger, "The tiger (Panthera tigris) is the largest extant cat species and a member of the genus Panthera. It is most recognisable for its dark vertical stripes on orange-brown fur with a lighter underside. It is an apex predator, primarily preying on ungulates such as deer and wild boar. It is territorial and generally a solitary but social predator, requiring large contiguous areas of habitat, which support its requirements for prey and rearing of its offspring. Tiger cubs stay with their mother for about two years, before they become independent and leave their mother's home range to establish their own."));
        //animalList.add(new Animal("Cheetah",  R.drawable.cheetah, "Description coming soon"));


    }


}