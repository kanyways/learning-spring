pragma solidity ^0.4.18;

contract Ticket {
    string _brand;
    address _owner;

    //
    constructor() public {
        _brand = "火车";
        // msg.sender 是当前合约的创建者
        _owner = msg.sender;
    }

    function setBrand(string brand) public {
        _brand = brand;
    }

    // 如果返回值当中有状态的变量值需要使用 constant
    function getBrand() constant public returns (string){
        return _brand;
    }

    // 如果只是纯粹的放回一个值，例如：固定的值，
    function sayHello() pure public returns (string) {
        return "Hello World";
    }

    // 如果当前返回的既不是固定值也不是状态变量，这个时候我们使用view
    function getCurrent() view public returns (address){
        return msg.sender;
    }

    // 如果当前的调用者是当前的创建合约的人才可以去销毁合约
    function kill() public {
        if (_owner == msg.sender) {
            selfdestruct(msg.sender);
        }
    }
}