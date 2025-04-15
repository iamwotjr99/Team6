'use client';

import Modal from '@/components/common/Modal';
import ModalPortal from '@/components/common/ModalPotal';
import {useModalStore} from '@/stores/Modal';

type Props = {
  children: React.ReactNode;
};

export default function ModalProvider({ children }: Props) {
  const isModalOpen = useModalStore.use.isModalOpen();
  const contents = useModalStore.use.contents();
  const setModal = useModalStore.use.setModal();

  return (
    <>
      {children}
      {isModalOpen && (
        <ModalPortal>
          <Modal onClose={() => setModal(null)}>{contents}</Modal>
        </ModalPortal>
      )}
    </>
  );
}