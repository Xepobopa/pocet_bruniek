<h1>This is a description for my homework.</h1>

I created a folder to store all images to test in projec dir:
</br>
<img width="237" alt="Screenshot 2025-03-19 at 22 45 25" src="https://github.com/user-attachments/assets/d61f951f-cc42-4310-84ad-b29a591d0241" />

I also wrote a <code>Test.java</code> file to test all this images.
So it load all images, and pass it to the main code, get result (cells counter) and comapare it with data from <code>vyseldy.txt</code> file.

So logs look like this:
</br>
<img width="342" alt="Screenshot 2025-03-19 at 22 46 24" src="https://github.com/user-attachments/assets/c4bc504b-71b4-47b9-b4b8-da0588c51bcf" />


When code analize count of cells it create a <code>..._analized.png</code> file in <code>test/results/..</code> forlder.
Ananlized images look like this:
<ul>
  <li><div>
    <b>SkenBuniek01_analyzed.png</b>
    <img width="798" alt="Screenshot 2025-03-19 at 22 46 39" src="https://github.com/user-attachments/assets/efd055df-6ac0-44de-a1ee-841ef4a9b1b0" />
  </div></li>

  <li><div>
    <b>SkenBuniek06_analyzed.png</b>
    <img width="799" alt="Screenshot 2025-03-19 at 22 47 05" src="https://github.com/user-attachments/assets/6ca7480f-331f-4590-b9e0-b09683853dd6" />
  </div></li>
</ul>

<h1>How code works</h1>
So my code is simple and have next steps:
<ul>
  <li>Analize every pixel (from top left to bottom right) with <code>jePixelBunky()</code> method.</li>
  <li>If we found some cell pixel, then i start getCellSection. It also analize image line by line. It iterate to the rightest cell pixel, then go to the one pixel bottom left and continue. Every iteration it compare curent x and y coord with <code>Section</code> and store the biggest / smallest values.
Section is just a class that contain left top and bottom right points, so it like container. We need it to understand that some rect is a cell, we also use it to not check already analized part of image.</li>
  <li><code>getCellSection</code> returns <code>Section</code>, we remember it and show it on image</li>
  <li>At the end we just save modified image.</li>

</ul>
