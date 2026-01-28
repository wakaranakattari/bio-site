import { cookies } from 'next/headers';

const translations = {
  es: {
    navigation: { home: 'Inicio', about: 'Sobre Mí', projects: 'Proyectos', contact: 'Contacto' },
    hero: {
      greeting: 'Hola, soy',
      name: 'Nikita',
      age: '17 años',
      role: 'Desarrollador Full-Stack',
      description: 'Creando soluciones elegantes con tecnologías modernas',
      viewProjects: 'Ver Proyectos',
      contact: 'Contactarme',
    },
    about: {
      title: 'Sobre',
      highlight: 'MÍ',
      bio: 'Soy Nikita...',
      journey: 'Comencé a programar...',
      philosophy: 'Creo en el código limpio...',
      location: 'Basado en Internet',
      education: 'Autodidacta & Comunidad',
      focus: 'Rendimiento & Experiencia de Usuario',
      skills: 'HABILIDADES & TECNOLOGÍAS',
    },
    projects: {
      title: 'MIS',
      highlight: 'PROYECTOS',
      description: 'Proyectos de código abierto en GitHub',
      stars: 'estrellas',
      forks: 'forks',
      updated: 'Actualizado',
      license: 'Licencia',
      noDescription: 'Sin descripción',
      error: 'No se pudieron cargar proyectos',
      noProjects: 'No hay proyectos',
    },
    contact: {
      title: 'Contacto',
      highlight: 'Conmigo',
      description: 'No dudes en contactarme a través de cualquiera de estos canales.',
    },
    footer: {
      rights: 'Todos los derechos reservados.',
      contact: { title: 'Contacto', highlight: 'Conmigo' },
    },
  },
  en: {
    navigation: { home: 'Home', about: 'About', projects: 'Projects', contact: 'Contact' },
    hero: {
      greeting: "Hi, I'm",
      name: 'Nikita',
      age: '17 years old',
      role: 'Full-Stack Developer',
      description: 'Creating elegant solutions with modern technologies',
      viewProjects: 'View Projects',
      contact: 'Contact Me',
    },
    about: {
      title: 'ABOUT',
      highlight: 'ME',
      bio: "I'm Nikita...",
      journey: 'Started programming...',
      philosophy: 'I believe in clean code...',
      location: 'Based on the Internet',
      education: 'Self-Taught & Community',
      focus: 'Performance & User Experience',
      skills: 'SKILLS & TECHNOLOGIES',
    },
    projects: {
      title: 'MY',
      highlight: 'PROJECTS',
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
      role: 'Full-Stack разработчик',
      description: 'Создаю элегантные решения на современных технологиях',
      viewProjects: 'Проекты',
      contact: 'Связаться',
    },
    about: {
      title: 'ОБО',
      highlight: 'МНЕ',
      bio: 'Я Никита...',
      journey: 'Начал программировать...',
      philosophy: 'Верю в чистый код...',
      location: 'На просторах интернета',
      education: 'Самоучка & Сообщество',
      focus: 'Производительность & UX',
      skills: 'НАВЫКИ & ТЕХНОЛОГИИ',
    },
    projects: {
      title: 'МОИ',
      highlight: 'ПРОЕКТЫ',
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
      role: '全栈开发者',
      description: '用现代技术创造优雅的解决方案',
      viewProjects: '查看项目',
      contact: '联系我',
    },
    about: {
      title: '关于',
      highlight: '我',
      bio: '我是Nikita...',
      journey: '14岁开始编程...',
      philosophy: '我相信干净的代码...',
      location: '基于互联网',
      education: '自学与社区',
      focus: '性能与用户体验',
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

export async function getLanguageServer() {
  const cookieStore = await cookies();
  const locale = (cookieStore.get('language')?.value as keyof typeof translations) || 'es';

  const t = (key: string): string => {
    const keys = key.split('.');
    let value: any = translations[locale];

    for (const k of keys) {
      if (value && value[k] !== undefined) {
        value = value[k];
      } else {
        return key;
      }
    }

    return typeof value === 'string' ? value : key;
  };

  return { locale, t };
}
