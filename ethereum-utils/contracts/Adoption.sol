pragma solidity ^0.4.17;

contract Adoption {
    address[16] public adopters;//保存领养者的地址

    function adopt(uint petId) public returns (uint) {
        require(petId >= 0 && petId <= 15);
        //保证当前Id在数组的范围内

        adopters[petId] = msg.sender;
        //保存调用者地址

        return petId;
    }

    // 返回领养者
    function getAdopters() public view returns (address[16]) {
        return adopters;
    }
}