export default function BreedingContainer(){
    return(<>
        {/* <div class="grid grid-flow-col grid-rows-3 gap-4 min-w-20 max-w-md">
          <div class="row-span-3 border border-black">01</div>
          <div class="col-span-2 border border-black ">02</div>
          <div class="col-span-2 border row-span-2 border-black">03</div>
        </div> */}



        <div class="grid grid-cols-5 grid-rows-4 gap-4  max-w-md">
            {/* Ark Cryo Image */}
            <div class="row-span-2 col-span-2 border border-black" ><img href="../assets/ArkCryoImages/Therizinosaur.png"></img>Image</div>
            <div class="grid grid-cols-2 grid-rows-7 row-span-4 col-span-3 border border-black">
            Stats
            </div>
            <div class="row-span-2 col-span-2 border border-black">
                Color Regions

            </div>
            


        </div>
    </>)
}