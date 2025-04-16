'use client';

import CircleButton from '@/components/common/CircleButton';
import NavBarMenu from './NavbarMenu';
import {ICON_SIZE} from '@/constants'
import {
  IoChatbubble,
  IoChatbubbleOutline,
  IoHeart,
  IoHeartOutline,
} from 'react-icons/io5';
import PlusIcon from '@/components/icon/PlusIcon';
import {useModalStore} from '@/stores/Modal';
import ChatCreateModal from '@/components/chat/ChatCreateModal';

const LEFT_MENUS = [
  {
    title: 'Chat',
    value: 'chat',
    path: '/chat',
    onIcon: <IoChatbubble size={ICON_SIZE} className='text-green-700'/>,
    offIcon: <IoChatbubbleOutline size={ICON_SIZE}/>
  },
];

const RIGHT_MENUS = [
  {
    title: 'My',
    value: 'mypage',
    path: '/mypage',
    onIcon: <IoHeart size={ICON_SIZE} className='text-green-700'/>,
    offIcon: <IoHeartOutline size={ICON_SIZE}/>
  }
];

export default function Navbar() {
  const setModal = useModalStore.use.setModal();

  const handleChatModal = () => {
    setModal(<ChatCreateModal />); 
  };
  
  return (
    <nav
      className="fixed bottom-0 left-1/2 z-40 flex h-16 w-full max-w-[430px] -translate-x-1/2 items-center justify-around border-t rounded-t-2xl bg-white">
      {LEFT_MENUS.map((menu) => (
        <NavBarMenu key={menu.value} menu={menu} />
      ))}
      <div className="relative -translate-y-5">
        <CircleButton
          onClick={handleChatModal}
          className="flex items-center justify-center rounded-t-full bg-green-700"
          size='lg'
        >
          <PlusIcon size='lg'/>
        </CircleButton>
      </div>
      {RIGHT_MENUS.map((menu) => (
        <NavBarMenu key={menu.value} menu={menu} />
      ))}
    </nav>
  );
}