import Vue from "vue";

import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faHeart as fasHeart ,faXmark, faPen } from "@fortawesome/free-solid-svg-icons";
import { faHeart as farHeart } from "@fortawesome/free-regular-svg-icons";

library.add(fasHeart, farHeart, faXmark, faPen);

Vue.component("font-awesome-icon", FontAwesomeIcon);

