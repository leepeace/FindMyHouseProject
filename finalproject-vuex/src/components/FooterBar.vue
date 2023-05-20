<template>
  <section class="footer">
    <div class="footer-container">
      <div class="footer-logo">
        <div class="footer-icon">
          <b-img
            :src="require('@/assets/houselogo.png')"
            id="logo"
            class="d-inline-block align-top"
            alt="logo"
            style="width: 80px; height: 80px; margin-top:10px"
          ></b-img>
        </div>
        <div class="footer-text">Where Is My Home</div>
      </div>

      <!-- <div class="links">
        <a href="" class="footer-item">Home</a>
        <a href="" class="footer-item">About</a>
        <a href="" class="footer-item">Contact</a>
      </div>

      <div class="links">
        <a href="" class="footer-item">Location</a>
        <a href="" class="footer-item">Social Links</a>
        <a href="" class="footer-item">Copyright</a>
      </div> -->
    </div>
  </section>
</template>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" />
<style media="screen">
.spacer {
  height: 535px;
  border: solid red 1px;
}

/* Colors and font sizes can be changed, everything else is required for a responsive footer. Links can be added and removed. The spacer above is not needed, it is just simulating space where content would normally go. */

.footer {
  width: 100%;
  height: 150px;
  background-color: rgb(35, 55, 107);
  color: white;
  display: flex;
  justify-content: center;
}

.footer-container {
  display: contents;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: center;
  padding: 20px 0px;
  width: 80%;
  max-width: 1280px;
}

.footer-logo {
  display: flex;
  align-items: center;
}

.footer-icon {
  min-width: 100px;
  min-height: 100px;
  border-radius: 50%;
  background: rgb(18, 29, 56);
}

.footer-text {
  margin: 0px 15px;
  cursor: default;
  user-select: none;
}

.footer-item {
  color: white;
  text-decoration: none;
  margin: 5px 0px;
  padding: 5px 10px 5px 10px;
}

.links {
  display: flex;
  flex-direction: column;
  text-align: start;
  margin: 20px 0px;
  border-left: solid white 1px;
  padding: 0px 50px;
}

.footer-item:hover {
  background: rgb(18, 29, 56);
}

@media (max-width: 770px) {
  .footer-logo {
    margin-left: auto;
    margin-right: auto;
  }

  .links {
    text-align: center;
    margin-left: auto;
    margin-right: auto;
    border-right: solid white 1px;
  }
}
</style>
