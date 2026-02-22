'use client';

import { createContext, useContext, useState, ReactNode, useEffect } from 'react';

export type Locale = 'en' | 'ru' | 'zh';

const translations = {
  en: {
    navigation: { home: 'Home', about: 'About', projects: 'Projects', contact: 'Contact' },
    hero: {
      greeting: "Hi, I'm",
      name: 'Nikita',
      age: '17 years old',
      role: 'C++ / Web Developer',
      description: 'I write code in C++ and do web development.',
      viewProjects: 'View Projects',
      contact: 'Contact Me',
    },
    about: {
      title: 'About',
      highlight: 'Me',
      bio: "My name is Nikita, I'm 17 years old. I write code in C++ and do web development.",
      journey: 'Started with C++ at 14, then learned web development. Now I use both.',
      philosophy: 'I like clean code and simple solutions.',
      location: 'Based on the Internet',
      education: 'Self-Taught & Community',
      focus: 'Systems & Web',
      skills: 'Skills & Technologies',
    },
    projects: {
      title: 'My',
      highlight: 'Projects',
      description: 'Open source projects on GitHub',
      stars: 'stars',
      forks: 'forks',
      updated: 'Updated',
      license: 'License',
      noDescription: 'No description',
      error: 'Failed to load projects',
      noProjects: 'No projects',
    },
    contact: {
      title: 'Contact',
      highlight: 'Me',
      description: 'Feel free to reach out through any of these channels.',
    },
    footer: { rights: 'All rights reserved.', contact: { title: 'Contact', highlight: 'Me' } },
  },
  ru: {
    navigation: { home: 'Главная', about: 'Обо мне', projects: 'Проекты', contact: 'Контакты' },
    hero: {
      greeting: 'Привет, я',
      name: 'Никита',
      age: '17 лет',
      role: 'C++ / Web разработчик',
      description: 'Пишу на C++ и занимаюсь веб-разработкой.',
      viewProjects: 'Проекты',
      contact: 'Связаться',
    },
    about: {
      title: 'Обо',
      highlight: 'Мне',
      bio: 'Меня зовут Никита, мне 17 лет. Пишу на C++ и занимаюсь веб-разработкой.',
      journey: 'Начал с C++ в 14 лет, потом освоил веб. Сейчас использую оба направления.',
      philosophy: 'Люблю чистый код и простые решения.',
      location: 'На просторах интернета',
      education: 'Самоучка & Сообщество',
      focus: 'Системы & Веб',
      skills: 'Навыки & Технологии',
    },
    projects: {
      title: 'Мои',
      highlight: 'Проекты',
      description: 'Проекты с открытым исходным кодом на GitHub',
      stars: 'звёзд',
      forks: 'форков',
      updated: 'Обновлён',
      license: 'Лицензия',
      noDescription: 'Нет описания',
      error: 'Не удалось загрузить',
      noProjects: 'Нет проектов',
    },
    contact: {
      title: 'Связаться',
      highlight: 'Со мной',
      description: 'Свяжитесь со мной через любой из этих каналов.',
    },
    footer: {
      rights: 'Все права защищены.',
      contact: { title: 'Связаться', highlight: 'Со мной' },
    },
  },
  zh: {
    navigation: { home: '首页', about: '关于我', projects: '项目', contact: '联系' },
    hero: {
      greeting: '你好，我是',
      name: 'Nikita',
      age: '17岁',
      role: 'C++ / 网页开发者',
      description: '写C++代码和做网页开发。',
      viewProjects: '查看项目',
      contact: '联系我',
    },
    about: {
      title: '关于',
      highlight: '我',
      bio: '我叫Nikita，17岁。写C++代码和做网页开发。',
      journey: '14岁开始学C++，后来学了网页开发。现在两种都用。',
      philosophy: '我喜欢干净的代码和简单的解决方案。',
      location: '基于互联网',
      education: '自学与社区',
      focus: '系统与网页',
      skills: '技能与技术',
    },
    projects: {
      title: '我的',
      highlight: '项目',
      description: 'GitHub上的开源项目',
      stars: '星标',
      forks: '分支',
      updated: '更新于',
      license: '许可证',
      noDescription: '暂无描述',
      error: '无法加载',
      noProjects: '暂无项目',
    },
    contact: { title: '联系', highlight: '我', description: '可以通过以下渠道联系我。' },
    footer: { rights: '保留所有权利。', contact: { title: '联系', highlight: '我' } },
  },
};

interface LanguageContextType {
  locale: Locale;
  setLocale: (locale: Locale) => void;
  t: (key: string) => string;
}

const LanguageContext = createContext<LanguageContextType | undefined>(undefined);

export function LanguageProvider({ children }: { children: ReactNode }) {
  const [locale, setLocale] = useState<Locale>('en');

  useEffect(() => {
    const saved = localStorage.getItem('language') as Locale;
    if (saved && translations[saved]) setLocale(saved);
  }, []);

  const changeLocale = (newLocale: Locale) => {
    setLocale(newLocale);
    localStorage.setItem('language', newLocale);
  };

  const t = (key: string): string => {
    const keys = key.split('.');
    let value: any = translations[locale];
    for (const k of keys) {
      if (value && value[k] !== undefined) value = value[k];
      else return key;
    }
    return typeof value === 'string' ? value : key;
  };

  return (
    <LanguageContext.Provider value={{ locale, setLocale: changeLocale, t }}>
      {children}
    </LanguageContext.Provider>
  );
}

export function useLanguage() {
  const context = useContext(LanguageContext);
  if (!context) throw new Error('useLanguage must be used within LanguageProvider');
  return context;
}